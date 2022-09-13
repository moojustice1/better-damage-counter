package com.betterdamagecounter;

import com.betterdamagecounter.objects.DamagedNpc;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.http.api.RuneLiteAPI;
import okhttp3.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static net.runelite.http.api.RuneLiteAPI.JSON;

@Slf4j
public class BetterDamageCounterClient {
    private final OkHttpClient client;
    private final HttpUrl apiBase;
    private final Gson gson;

    @Getter
    @Setter
    private UUID uuid;

    @Inject
    private BetterDamageCounterClient(OkHttpClient client, @Named("runelite.api.base") HttpUrl apiBase, Gson gson)
    {
        this.client = client;
        this.apiBase = apiBase;
        this.gson = gson;
    }

    public CompletableFuture<Void> submit(Collection<DamagedNpc> damagedNpcs)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        HttpUrl url = apiBase.newBuilder()
                .addPathSegment("betterdamagecounter")
                .build();

        Request.Builder requestBuilder = new Request.Builder();
        if (uuid != null)
        {
            requestBuilder.header(RuneLiteAPI.RUNELITE_AUTH, uuid.toString());
        }
        requestBuilder.post(RequestBody.create(JSON, gson.toJson(damagedNpcs)))
                .url(url)
                .build();

        client.newCall(requestBuilder.build()).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.warn("unable to submit damage", e);
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                if (response.isSuccessful())
                {
                    log.debug("Submitted damage");
                }
                else
                {
                    log.warn("Error submitting damage: {} - {}", response.code(), response.message());
                }
                response.close();
                future.complete(null);
            }
        });

        return future;
    }

    public Collection<DamagedNpc> get() throws IOException
    {
        HttpUrl url = apiBase.newBuilder()
                .addPathSegment("betterdamagecounter")
                .build();

        Request request = new Request.Builder()
                .header(RuneLiteAPI.RUNELITE_AUTH, uuid.toString())
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                log.debug("Error looking up damage: {}", response);
                return null;
            }

            InputStream in = response.body().byteStream();
            // CHECKSTYLE:OFF
            return gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
                    new TypeToken<List<DamagedNpc>>(){}.getType());
            // CHECKSTYLE:ON
        }
        catch (JsonParseException ex)
        {
            throw new IOException(ex);
        }
    }

    public void delete(String eventId)
    {
        HttpUrl.Builder builder = apiBase.newBuilder()
                .addPathSegment("damage");

        if (eventId != null)
        {
            builder.addQueryParameter("eventId", eventId);
        }

        Request request = new Request.Builder()
                .header(RuneLiteAPI.RUNELITE_AUTH, uuid.toString())
                .delete()
                .url(builder.build())
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.warn("unable to delete damage", e);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                log.debug("Deleted damage");
                response.close();
            }
        });
    }
}
