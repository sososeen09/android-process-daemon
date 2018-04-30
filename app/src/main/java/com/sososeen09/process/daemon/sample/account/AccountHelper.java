package com.sososeen09.process.daemon.sample.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by yunlong on 2018/4/30.
 */

public class AccountHelper {
    private static final String TAG = "AccountHelper";

    public static final String ACCOUNT_TYPE = "com.sososeen09.process.daemon.account";
    public static final String AUTHORITY = "com.sososeen09.process.daemon.provider";

    /**
     * 用于添加一个账户
     *
     * @param context
     */
    public static void addAccount(Context context) {
        AccountManager am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        //获得此类型的账户
        Account[] accounts = am.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length > 0) {
            Log.e(TAG, "sososeen09 account has exit");

            return;
        }
        //给这个账户类型添加一个账户
        Account sosoAccount = new Account("sososeen09", ACCOUNT_TYPE);
        am.addAccountExplicitly(sosoAccount, "soso", new Bundle());
    }

    /**
     * 设置 账户自动同步
     */
    public static void autoSync() {
        Account sosoAccount = new Account("sososeen09", ACCOUNT_TYPE);
        //设置同步
        ContentResolver.setIsSyncable(sosoAccount, AUTHORITY, 1);
        //自动同步
        ContentResolver.setSyncAutomatically(sosoAccount, AUTHORITY, true);
        //设置同步周期
        ContentResolver.addPeriodicSync(sosoAccount, AUTHORITY, new Bundle(), 1);

    }
}
