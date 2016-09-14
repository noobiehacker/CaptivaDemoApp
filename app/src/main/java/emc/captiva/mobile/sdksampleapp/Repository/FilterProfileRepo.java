package emc.captiva.mobile.sdksampleapp.Repository;

import java.util.List;

import emc.captiva.mobile.sdksampleapp.Model.Filter;
import emc.captiva.mobile.sdksampleapp.Model.FilterProfile;
import emc.captiva.mobile.sdksampleapp.Service.CreateProfileService;
import emc.captiva.mobile.sdksampleapp.Util.FilterRepoUtil;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by david on 9/1/16.
 */
public class FilterProfileRepo implements CreateProfileService{

    public static int defaultId = 0;
    public static List<FilterProfile> profilesCache;
    public static Number maxIdCache = 0;
    public Realm realm;
    //Write Profile To Disk

    public FilterProfileRepo(Realm realm) {
        this.realm = realm;
    }

    public FilterProfileRepo() {
    }

    @Override
    public void createAndPersistProfile(final FilterProfile profile, final Realm realm,
                                        Realm.Transaction.OnSuccess onSuccess,
                                        Realm.Transaction.OnError onError){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                FilterProfileRepo.this.createFilterProfileSync(bgrealm, profile);
            }
        },onSuccess,onError);

    }

    public void createFilterProfileSync(Realm bgrealm,final FilterProfile profile){

        //Persist Profile
        FilterProfile dbObj  = bgrealm.createObject(FilterProfile.class);
        dbObj.setAutoMaticallyApplyFilter(profile.isAutoMaticallyApplyFilter());
        dbObj.setId(profile.getId());
        dbObj.setFilters(createFilterList(profile.getFilters(), bgrealm));
        dbObj.setProfileName(profile.getProfileName());

    }

    private RealmList<Filter> createFilterList(RealmList<Filter> input ,Realm bgrealm){

        return new FilterRepoUtil().createReproFilters(input, bgrealm);
    }

    @Override
    public void readProfiles(final Realm realm, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                FilterProfileRepo.this.readListOfProfileSync(bgrealm);
            }
        },onSuccess,onError);

    }

    public void readProfiles(Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {

        this.realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                FilterProfileRepo.this.readListOfProfileSync(bgrealm);
            }
        },onSuccess,onError);

    }

    public void readListOfProfileSync(Realm bgrealm){

        //Read list Of profile
        RealmResults<FilterProfile> results = bgrealm.where(FilterProfile.class).findAll();
        this.profilesCache = bgrealm.copyFromRealm(results);
    }

    public void readMaxId(final Realm realm ,Realm.Transaction.OnSuccess onSuccess,
                          Realm.Transaction.OnError onError){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                FilterProfileRepo.this.readMaxIdSync(bgrealm);
            }
        },onSuccess,onError);

    }

    public void readMaxIdSync(final Realm realm){

        //Read list Of profile
        this.maxIdCache = realm.where(FilterProfile.class).max("id");

    }

}
