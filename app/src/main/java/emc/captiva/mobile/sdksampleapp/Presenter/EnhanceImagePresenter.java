package emc.captiva.mobile.sdksampleapp.Presenter;
import android.os.Bundle;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import emc.captiva.mobile.sdksampleapp.Constant;
import emc.captiva.mobile.sdksampleapp.EnhanceImageActivity;
import emc.captiva.mobile.sdksampleapp.Model.Filter;
import emc.captiva.mobile.sdksampleapp.Model.FilterProfile;
import emc.captiva.mobile.sdksampleapp.R;
import emc.captiva.mobile.sdksampleapp.Repository.FilterProfileRepo;
import emc.captiva.mobile.sdksampleapp.Util.FilterToMenuUtil;
import io.realm.Realm;

/**
 * Created by Davix on 9/14/16.
 */

public class EnhanceImagePresenter implements Realm.Transaction.OnError, Realm.Transaction.OnSuccess{

    private FilterProfileRepo repo;
    private EnhanceImageActivity activity;

    public EnhanceImagePresenter(EnhanceImageActivity activity,FilterProfileRepo repo) {
        this.repo = repo;
        this.activity = activity;
    }

    public void loadFilterProfile(int profileId , Realm.Transaction.OnSuccess onSuccess
            , Realm.Transaction.OnError onError){
        if(profileId != Constant.invalidId)
            this.repo.loadProfileAsync(profileId, onSuccess, onError);
    }

    @Override
    public void onError(Throwable error) {
        this.activity.displayCustomToast("Load Profile", "Failed", error.toString());
    }

    @Override
    public void onSuccess() {

        FilterProfile profileLoaded = FilterProfileRepo.lastProfileLoaded;
        if(profileLoaded != null){
            List<MenuItem> menuItems = this.createSequenceOfFilters(profileLoaded);
            this.activity.attemptToApplyFilters(menuItems);
        }

    }

    private MenuItem createMenuOptionFromFilterString(String item){
        return new FilterToMenuUtil().getMenuFromFilterString(item);
    }

    public List<MenuItem> createSequenceOfFilters(FilterProfile profile){

        List<MenuItem> menuList = new ArrayList<MenuItem>();
        List<Filter> filterList = profile.getFilters();
        if(filterList!=null){
            for(Filter item : filterList){
                MenuItem menu = this.createMenuOptionFromFilterString(item.filterName);
                menuList.add(menu);
            }
        }
        return menuList;
    }

    public int getProfileId(Bundle bundle) {

        if (bundle != null) {
            String key = this.activity.getString(R.string.intent_profile_key);
            if (bundle.containsKey(key))
                return bundle.getInt(key);
        }
        return Constant.invalidId;

    }

}
