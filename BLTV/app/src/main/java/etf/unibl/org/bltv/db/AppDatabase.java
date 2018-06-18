package etf.unibl.org.bltv.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.util.GlideApp;
import etf.unibl.org.bltv.util.ImageSaver;

@Database(entities = {News.class,Item.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract NewsDao newsDao();
    public abstract ItemDao itemDao();
    private static Context ctx;
    public static synchronized AppDatabase getAppDatabase(Context context) {
        ctx=context;
        if (INSTANCE == null) {
            INSTANCE=Room.databaseBuilder(context, AppDatabase.class,context.getString(R.string.database_name) ).build();
            INSTANCE.populateInitialData();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private void  populateInitialData() {
       if (itemDao().count() == 0) {
            Item[] items = new Item[]{
                    new Item(null, ctx.getString(R.string.kastel_name), ctx.getString(R.string.kastel_description), ctx.getString(R.string.kastel_url),
                            Float.parseFloat(ctx.getString(R.string.kastel_latitude)), Float.parseFloat(ctx.getString(R.string.kastel_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.banj_name), ctx.getString(R.string.banj_description), ctx.getString(R.string.banj_url),
                            Float.parseFloat(ctx.getString(R.string.banj_latitude)), Float.parseFloat(ctx.getString(R.string.banj_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.hotel_atina_name), ctx.getString(R.string.hotel_atina_description), ctx.getString(R.string.hotel_atina_url),
                            Float.parseFloat(ctx.getString(R.string.hotel_atina_latitude)), Float.parseFloat(ctx.getString(R.string.hotel_atina_longitude)),
                            false, Item.HOTEL, Integer.parseInt(ctx.getString(R.string.hotel_atina_rating))),
                    new Item(null, ctx.getString(R.string.gospodska_name), ctx.getString(R.string.gospodska_description), ctx.getString(R.string.gospodska_url),
                            Float.parseFloat(ctx.getString(R.string.gospodska_latitude)), Float.parseFloat(ctx.getString(R.string.gospodska_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.ferhadija_name), ctx.getString(R.string.ferhadija_description), ctx.getString(R.string.ferhadija_url),
                            Float.parseFloat(ctx.getString(R.string.ferhadija_latitude)), Float.parseFloat(ctx.getString(R.string.ferhadija_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.hram_name), ctx.getString(R.string.hram_description), ctx.getString(R.string.hram_url),
                            Float.parseFloat(ctx.getString(R.string.hram_latitude)), Float.parseFloat(ctx.getString(R.string.hram_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.muzej_name), ctx.getString(R.string.muzej_description), ctx.getString(R.string.muzej_url),
                            Float.parseFloat(ctx.getString(R.string.muzej_latitude)), Float.parseFloat(ctx.getString(R.string.muzej_longitude)),
                            false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.muzejs_name), ctx.getString(R.string.muzejs_description), ctx.getString(R.string.muzejs_url),
                            Float.parseFloat(ctx.getString(R.string.muzejs_latitude)), Float.parseFloat(ctx.getString(R.string.muzejs_longitude)),
                            false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.krupa_name), ctx.getString(R.string.krupa_description), ctx.getString(R.string.krupa_url),
                            Float.parseFloat(ctx.getString(R.string.krupa_latitude)), Float.parseFloat(ctx.getString(R.string.krupa_longitude)),
                            false, Item.MONUMENT, 0),
                    new Item(null, ctx.getString(R.string.hotel_bosna_name), ctx.getString(R.string.hotel_bosna_description), ctx.getString(R.string.hotel_bosna_url),
                            Float.parseFloat(ctx.getString(R.string.hotel_bosna_latitude)), Float.parseFloat(ctx.getString(R.string.hotel_bosna_longitude)),
                            false, Item.HOTEL, Integer.parseInt(ctx.getString(R.string.hotel_bosna_rating))),
                    new Item(null, ctx.getString(R.string.hotel_fortuna_name), ctx.getString(R.string.hotel_fortuna_description), ctx.getString(R.string.hotel_fortuna_url),
                            Float.parseFloat(ctx.getString(R.string.hotel_fortuna_latitude)), Float.parseFloat(ctx.getString(R.string.hotel_fortuna_longitude)),
                            false, Item.HOTEL, Integer.parseInt(ctx.getString(R.string.hotel_fortuna_rating))),
                    new Item(null, ctx.getString(R.string.hotel_vrbas_name), ctx.getString(R.string.hotel_vrbas_description), ctx.getString(R.string.hotel_vrbas_url),
                            Float.parseFloat(ctx.getString(R.string.hotel_vrbas_latitude)), Float.parseFloat(ctx.getString(R.string.hotel_vrbas_longitude)),
                            false, Item.HOTEL, Integer.parseInt(ctx.getString(R.string.hotel_vrbas_rating))),
                    new Item(null, ctx.getString(R.string.hotel_talija_name), ctx.getString(R.string.hotel_talija_description), ctx.getString(R.string.hotel_talija_url),
                            Float.parseFloat(ctx.getString(R.string.hotel_talija_latitude)), Float.parseFloat(ctx.getString(R.string.hotel_talija_longitude)),
                            false, Item.HOTEL, Integer.parseInt(ctx.getString(R.string.hotel_talija_rating))),
                    new Item(null, ctx.getString(R.string.zgrada_uprave_name), ctx.getString(R.string.zgrada_uprave_description), ctx.getString(R.string.zgrada_uprave_url),
                            Float.parseFloat(ctx.getString(R.string.zgrada_uprave_latitude)), Float.parseFloat(ctx.getString(R.string.zgrada_uprave_longitude)),
                            false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.palata_republike_name), ctx.getString(R.string.palata_republike_description), ctx.getString(R.string.palata_republike_url),
                    Float.parseFloat(ctx.getString(R.string.palata_republike_latitude)), Float.parseFloat(ctx.getString(R.string.palata_republike_longitude)),
                    false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.narodno_pozoriste_name), ctx.getString(R.string.narodno_pozoriste_description), ctx.getString(R.string.narodno_pozoriste_url),
                    Float.parseFloat(ctx.getString(R.string.narodno_pozoriste_latitude)), Float.parseFloat(ctx.getString(R.string.narodno_pozoriste_longitude)),
                    false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.gibonni_name), ctx.getString(R.string.gibonni_description), ctx.getString(R.string.gibonni_url),
                            Float.parseFloat(ctx.getString(R.string.gibonni_latitude)), Float.parseFloat(ctx.getString(R.string.gibonni_longitude)),
                            false, Item.EVENT, 0),
                    new Item(null, ctx.getString(R.string.jelena_tomasevic_name), ctx.getString(R.string.jelena_tomasevic_description), ctx.getString(R.string.jelena_tomasevic_url),
                            Float.parseFloat(ctx.getString(R.string.jelena_tomasevic_latitude)), Float.parseFloat(ctx.getString(R.string.jelena_tomasevic_longitude)),
                            false, Item.EVENT, 0),
                    new Item(null, ctx.getString(R.string.demofest_name), ctx.getString(R.string.demofest_description), ctx.getString(R.string.demofest_url),
                            Float.parseFloat(ctx.getString(R.string.demofest_latitude)), Float.parseFloat(ctx.getString(R.string.demofest_longitude)),
                            false, Item.EVENT, 0),
                    new Item(null, ctx.getString(R.string.imperativ_name), ctx.getString(R.string.imperativ_description), ctx.getString(R.string.imperativ_url),
                            Float.parseFloat(ctx.getString(R.string.imperativ_latitude)), Float.parseFloat(ctx.getString(R.string.imperativ_longitude)),
                            false, Item.EVENT, 0),
                    new Item(null, ctx.getString(R.string.bioskop_name), ctx.getString(R.string.bioskop_description), ctx.getString(R.string.bioskop_url),
                            Float.parseFloat(ctx.getString(R.string.bioskop_latitude)), Float.parseFloat(ctx.getString(R.string.bioskop_longitude)),
                            false, Item.EVENT, 0),
                    new Item(null, ctx.getString(R.string.sokolski_name), ctx.getString(R.string.sokolski_description), ctx.getString(R.string.sokolski_url),
                            Float.parseFloat(ctx.getString(R.string.sokolski_latitude)), Float.parseFloat(ctx.getString(R.string.sokolski_longitude)),
                            false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.stadion_name), ctx.getString(R.string.stadion_description), ctx.getString(R.string.stadion_url),
                            Float.parseFloat(ctx.getString(R.string.stadion_latitude)), Float.parseFloat(ctx.getString(R.string.stadion_longitude)),
                            false, Item.INSTITUTION, 0),
                    new Item(null, ctx.getString(R.string.aquana_name), ctx.getString(R.string.aquana_description), ctx.getString(R.string.aquana_url),
                            Float.parseFloat(ctx.getString(R.string.aquana_latitude)), Float.parseFloat(ctx.getString(R.string.aquana_longitude)),
                            false, Item.INSTITUTION, 0),
            };
            beginTransaction();
            try {
                for (int i = 0; i < items.length; i++) {
                    if(items[i].getPath()==null){
                        try {
                            Bitmap theBitmap = GlideApp.
                                    with(ctx).asBitmap().
                                    load(items[i].getUrl()).centerCrop().submit().get();
                            String path= ImageSaver.saveToInternalStorage(theBitmap,ctx);
                            items[i].setPath(path);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    itemDao().insert(items[i]);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
