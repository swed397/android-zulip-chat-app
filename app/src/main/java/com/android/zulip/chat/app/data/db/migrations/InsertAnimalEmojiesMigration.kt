package com.android.zulip.chat.app.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val INSERT_ANIMALS_EMOJIS_MIGRATION = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "insert into emojis (emoji_name, emoji_code) values" +
                    "('Grinning Cat Face With Smiling Eyes', 0x1F638)," +
                    "('Grinning Cat Face With Smiling Eyes and Smiling Mouth', 0x1F639)," +
                    "('Cat Face With Tears of Joy', 0x1F63B)," +
                    "('Smiling Cat Face With Heart-Eyes', 0x1F63A)," +
                    "('Cat Face With Wry Smile', 0x1F63C)," +
                    "('Kissing Cat Face With Closed Eyes', 0x1F63F)," +
                    "('Pouting Cat Face', 0x1F640)," +
                    "('Crying Cat Face', 0x1F63F)," +
                    "('Weary Cat Face', 0x1F640)," +
                    "('Face of a Monkey', 0x1F412)," +
                    "('Monkey Face', 0x1F435)," +
                    "('Gorilla', 0x1F98D)," +
                    "('Orangutan', 0x1F9A7)," +
                    "('Dog Face', 0x1F436)," +
                    "('Poodle', 0x1F429)," +
                    "('Wolf', 0x1F43A)," +
                    "('Fox', 0x1F98A)," +
                    "('Raccoon', 0x1F99D)," +
                    "('Cat', 0x1F431)," +
                    "('Lion', 0x1F981)," +
                    "('Tiger', 0x1F42F)," +
                    "('Leopard', 0x1F406)," +
                    "('Horse', 0x1F434)," +
                    "('Unicorn', 0x1F984)," +
                    "('Cow', 0x1F42E)," +
                    "('Ox', 0x1F402)," +
                    "('Water Buffalo', 0x1F403)," +
                    "('Cow Face', 0x1F404)," +
                    "('Pig', 0x1F437)," +
                    "('Pig Face', 0x1F43D)," +
                    "('Boar', 0x1F417)," +
                    "('Pig Nose', 0x1F437)," +
                    "('Ram', 0x1F40F)," +
                    "('Ewe', 0x1F411)," +
                    "('Goat', 0x1F410)," +
                    "('Camel', 0x1F42A)," +
                    "('Two-Hump Camel', 0x1F42B)," +
                    "('Sheep', 0x1F411)," +
                    "('Elephant', 0x1F418)," +
                    "('Rhinoceros', 0x1F98F)," +
                    "('Mouse', 0x1F42D)," +
                    "('Mouse Face', 0x1F401)," +
                    "('Rat', 0x1F400)," +
                    "('Hamster', 0x1F439)," +
                    "('Rabbit', 0x1F430)," +
                    "('Rabbit Face', 0x1F407)," +
                    "('Chipmunk', 0x1F43F)," +
                    "('Hedgehog', 0x1F994)," +
                    "('Bat', 0x1F987)," +
                    "('Bear', 0x1F43B)," +
                    "('Koala', 0x1F428)," +
                    "('Panda', 0x1F43C)," +
                    "('Paw Prints', 0x1F43E)," +
                    "('Eye', 0x1F441)," +
                    "('Nose', 0x1F443)," +
                    "('Mouth', 0x1F444)," +
                    "('Ear', 0x1F442)," +
                    "('Ear With Hearing Aid', 0x1F9BB)," +
                    "('Tongue', 0x1F445)," +
                    "('Brain', 0x1F9E0)," +
                    "('Tooth', 0x1F9B7)," +
                    "('Bone', 0x1F9B4)," +
                    "('Footprints', 0x1F43E)," +
                    "('Paw Prints', 0x1F43E)," +
                    "('Wolf Face', 0x1F43A)," +
                    "('Frog', 0x1F438)," +
                    "('Crocodile', 0x1F40A)," +
                    "('Turtle', 0x1F422)," +
                    "('Lizard', 0x1F98E)," +
                    "('Snake', 0x1F40D)," +
                    "('Dragon', 0x1F409)," +
                    "('Dragon Face', 0x1F432)," +
                    "('Spouting Whale', 0x1F433)," +
                    "('Whale', 0x1F40B)," +
                    "('Dolphin', 0x1F42C)," +
                    "('Fish', 0x1F41F)," +
                    "('Tropical Fish', 0x1F420)," +
                    "('Blowfish', 0x1F421)," +
                    "('Shark', 0x1F988)," +
                    "('Octopus', 0x1F419)," +
                    "('Spiral Shell', 0x1F41A)," +
                    "('Snail', 0x1F40C)," +
                    "('Butterfly', 0x1F98B)," +
                    "('Bug', 0x1F41B)," +
                    "('Ant', 0x1F41C)," +
                    "('Honeybee', 0x1F41D)," +
                    "('Bee', 0x1F41D)," +
                    "('Lady Beetle', 0x1F41E)," +
                    "('Cricket', 0x1F997)," +
                    "('Spider', 0x1F577)," +
                    "('Spider Web', 0x1F578)," +
                    "('Scorpion', 0x1F982)," +
                    "('Mosquito', 0x1F99F)," +
                    "('Microbe', 0x1F9A0)," +
                    "('Bouquet', 0x1F490)," +
                    "('Cherry Blossom', 0x1F338)," +
                    "('White Flower', 0x1F4AE)"
        )
    }
}

