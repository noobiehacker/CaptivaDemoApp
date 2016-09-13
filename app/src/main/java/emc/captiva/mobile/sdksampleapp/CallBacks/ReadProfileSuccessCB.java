package emc.captiva.mobile.sdksampleapp.CallBacks;

import emc.captiva.mobile.sdksampleapp.ActivityHelper.MainActivityPresenter;
import io.realm.Realm;

/**
 * Created by Davix on 9/13/16.
 */

public class ReadProfileSuccessCB implements Realm.Transaction.OnSuccess {

    MainActivityPresenter presenter;

    public ReadProfileSuccessCB(MainActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSuccess() {

        this.presenter.loadProfileIntoSpinner();
    }
}
