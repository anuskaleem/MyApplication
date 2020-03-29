package com.example.apple.databaseapplication;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MIgration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        int version = (int)oldVersion;
        switch(version) {
            case 0:
            {
                schema.get("Person").addField("id",long.class, FieldAttribute.PRIMARY_KEY);
            }
        }
    }
}
