package com.example.talevincontacts.utils;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

public class IntentUtils {
    public static void openPage(Context from, Class to) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
    }

    public static void openPageWithExtras(Context from, Class to, Extra... extras) {
        Intent intent = new Intent(from, to);

        for (Extra extra : extras) {
            intent.putExtra(extra.getKey(), extra.getObject());
        }

        from.startActivity(intent);
    }

    public static class Extra {
        private String key;
        private Serializable object;

        public String getKey() {
            return key;
        }

        public Extra setKey(String key) {
            this.key = key;
            return this;
        }

        public Serializable getObject() {
            return object;
        }

        public Extra setObject(Serializable object) {
            this.object = object;
            return this;
        }
    }
}
