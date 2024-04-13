package com.android.zulip.chat.app.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val INSERT_EMOJIS_MIGRATION = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "insert into emojis (emoji_name, emoji_code) values" +
                    "('Grinning Face',0x1F600)," +
                    "('Money,Mouth Face',0x1F60D)," +
                    "('Sunglasses',0x1F60E)," +
                    "('Face With Tears of Joy',0x1F602)," +
                    "('Grinning Face With Big Eyes',0x1F603)," +
                    "('Grinning Face With Smiling Eyes',0x1F604)," +
                    "('Face With Heart,Shaped Eyes',0x1F60D)," +
                    "('Face Blowing a Kiss',0x1F618)," +
                    "('Kissing Face With Closed Eyes',0x1F61A)," +
                    "('Face With Stuck,Out Tongue',0x1F61B)," +
                    "('Face Without Mouth',0x1F636)," +
                    "('Sleeping Face',0x1F634)," +
                    "('Pensive Face',0x1F614)," +
                    "('Face With Medical Mask',0x1F637)," +
                    "('Face With Thermometer',0x1F912)," +
                    "('Face With Head,Bandage',0x1F915)," +
                    "('Nauseated Face',0x1F922)," +
                    "('Face Vomiting',0x1F92E)," +
                    "('Sneezing Face',0x1F927)," +
                    "('Hot Face',0x1F975)," +
                    "('Cold Face',0x1F976)," +
                    "('Woozy Face',0x1F974)," +
                    "('Dizzy Face',0x1F635)," +
                    "('Exploding Head',0x1F92F)," +
                    "('Cowboy Hat Face',0x1F920)," +
                    "('Clown Face',0x1F921)," +
                    "('Nerd Face',0x1F913)," +
                    "('Face With Monocle',0x1F9D0)," +
                    "('Face With Diagonal Mouth',0x1F636)," +
                    "('Face With Hand Over Mouth',0x1F92D)," +
                    "('Face With Open Mouth',0x1F62E)," +
                    "('Hushed Face',0x1F62F)," +
                    "('Astonished Face',0x1F632)," +
                    "('Flushed Face',0x1F633)," +
                    "('Pleading Face',0x1F97A)," +
                    "('Frowning Face With Open Mouth',0x1F626)," +
                    "('Anguished Face',0x1F627)," +
                    "('Fearful Face',0x1F628)," +
                    "('Anxious Face With Sweat',0x1F630)," +
                    "('Sad But Relieved Face',0x1F625), " +
                    "('Crying Face', 0x1F622 )," +
                    "('Loudly Crying Face', 0x1F62D )," +
                    "('Face Screaming in Fear', 0x1F631 )," +
                    "('Confounded Face', 0x1F616 )," +
                    "('Persevering Face', 0x1F623 )," +
                    "('Disappointed Face', 0x1F61E )," +
                    "('Downcast Face With Sweat', 0x1F613 )," +
                    "('Weary Face', 0x1F629 )," +
                    "('Tired Face', 0x1F62B )," +
                    "('Grimacing Face', 0x1F62C )," +
                    "('Face With Rolling Eyes', 0x1F644 )," +
                    "('Face With No Good Gesture', 0x1F645 )," +
                    "('Face With OK Gesture', 0x1F646 )," +
                    "('Person Tipping Hand', 0x1F481 )," +
                    "('Information Desk Person', 0x1F481 )," +
                    "('Person Raising Hand', 0x1F64B )," +
                    "('Person With Folded Hands', 0x1F64F )," +
                    "('Person Frowning', 0x1F64D )," +
                    "('Person Pouting', 0x1F64E )," +
                    "('Person Gesturing No', 0x1F645 )," +
                    "('Person Gesturing OK', 0x1F646 )," +
                    "('Person Bowing', 0x1F647 )," +
                    "('Person Raising Both Hands in Celebration', 0x1F64C )," +
                    "('Person With Ball', 0x26F9 )," +
                    "('Person With Pouting Face', 0x1F64E )," +
                    "('Person With Folded Arms', 0x1F64C )," +
                    "('Person Shrugging', 0x1F937 )," +
                    "('Pregnant Woman', 0x1F930 )," +
                    "('Breast-Feeding', 0x1F931 )," +
                    "('Baby Angel', 0x1F47C)," +
                    "('Santa Claus', 0x1F385 )," +
                    "('Mrs. Claus', 0x1F936 )," +
                    "('Superhero', 0x1F9B8 )," +
                    "('Supervillain', 0x1F9B9 )," +
                    "('Princess', 0x1F478 )," +
                    "('Prince', 0x1F934 )," +
                    "('Person Wearing Turban', 0x1F473 )," +
                    "('Person With Skullcap', 0x1F474 )," +
                    "('Woman With Headscarf', 0x1F9D5 )"
        )

        db.execSQL(
            "insert into emojis (emoji_name, emoji_code) values " +
                    "('Person in Tuxedo', 0x1F935 )," +
                    "('Person With Veil', 0x1F470 )," +
                    "('Bride With Veil', 0x1F470 )," +
                    "('Pregnant Woman', 0x1F930 )," +
                    "('Breast-Feeding', 0x1F931 )," +
                    "('Baby', 0x1F476 )," +
                    "('Child', 0x1F9D2 )," +
                    "('Boy', 0x1F466 )," +
                    "('Girl', 0x1F467 )," +
                    "('Man', 0x1F468 )," +
                    "('Woman', 0x1F469 )," +
                    "('Older Person', 0x1F9D3 )," +
                    "('Old Man', 0x1F474 )," +
                    "('Old Woman', 0x1F475 )," +
                    "('Person With Blond Hair', 0x1F471 )," +
                    "('Man With Gua Pi Mao', 0x1F472 )," +
                    "('Man With Turban', 0x1F473 )," +
                    "('Woman With Headscarf', 0x1F9D5 )," +
                    "('Police Officer', 0x1F46E )," +
                    "('Construction Worker', 0x1F477 )," +
                    "('Guard', 0x1F482 )," +
                    "('Detective', 0x1F575)," +
                    "('Spy', 0x1F575 )," +
                    "('Health Worker', 0x1F9D1)," +
                    "('Farmer', 0x1F468 )," +
                    "('Cook', 0x1F468 )," +
                    "('Mechanic', 0x1F468 )," +
                    "('Factory Worker', 0x1F468 )," +
                    "('Office Worker', 0x1F468 )," +
                    "('Scientist', 0x1F468 )," +
                    "('Technologist', 0x1F468 )," +
                    "('Singer', 0x1F468 )," +
                    "('Artist', 0x1F468 )," +
                    "('Pilot', 0x1F468 )," +
                    "('Astronaut', 0x1F468 )," +
                    "('Firefighter', 0x1F468 )," +
                    "('Person With Crown', 0x1F934 )," +
                    "('Person With White Cane', 0x1F468 )," +
                    "('Person in Motorized Wheelchair', 0x1F468 )," +
                    "('Person in Manual Wheelchair', 0x1F468 )," +
                    "('Person Running', 0x1F3C3 )," +
                    "('Woman Running', 0x1F3C3)," +
                    "('Man Running', 0x1F3C3)," +
                    "('Person Walking', 0x1F6B6 )," +
                    "('Woman Walking', 0x1F6B6)," +
                    "('Man Walking', 0x1F6B6 )," +
                    "('Person Standing', 0x1F9CD )," +
                    "('Woman Standing', 0x1F9CD )," +
                    "('Man Standing', 0x1F9CD )," +
                    "('Person Kneeling', 0x1F9CE )," +
                    "('Woman Kneeling', 0x1F9CE )," +
                    "('Man Kneeling', 0x1F9CE)," +
                    "('Person With Probing Cane', 0x1F468 )," +
                    "('Person in Motorized Wheelchair', 0x1F468 )," +
                    "('Person in Manual Wheelchair', 0x1F468 )"
        )
    }
}

