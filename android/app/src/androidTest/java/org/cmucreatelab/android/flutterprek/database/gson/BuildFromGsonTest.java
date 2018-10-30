package org.cmucreatelab.android.flutterprek.database.gson;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.cmucreatelab.android.flutterprek.database.GsonDatabaseParser;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BuildFromGsonTest {


    @Test
    public void buildObjectFromGsonDbSeed() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        JsonParser parser = new JsonParser();

        // build object and check attributes for correctness
        InputStream inputStream = appContext.getAssets().open("DbSeed.json");
        JsonElement e1 = parser.parse(new InputStreamReader(inputStream));
        GsonDatabaseParser gsonDatabaseParser = new Gson().fromJson(e1, GsonDatabaseParser.class);
        assertEquals(1, gsonDatabaseParser.classrooms.size());

        // check that the constructed object is equal to the original JSON
        String jsonString = new Gson().toJson(gsonDatabaseParser, GsonDatabaseParser.class);
        JsonElement e2 = parser.parse(jsonString);
        assertEquals(e1, e2);
    }

}