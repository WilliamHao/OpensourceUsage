package com.example.williamhao.base;

import android.os.Bundle;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-2-21
 * TODO 
 */

public abstract class BasePageActivity extends BaseActivity {
    protected Bundle mBundle;
	public static Bundle forSynBundle;//only for ListFgmActivity and MemberActivity to convert params,have better don't use it for others

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
        mBundle = getIntent().getExtras();
		forSynBundle = getIntent().getExtras();
		setLayoutView();
		init(bundle);
	}
	private void init(Bundle bundle) {
		findViews();
		setupViews(bundle);
		setListener();
		fetchData();
	}


	protected abstract void setLayoutView();
	
	protected abstract void findViews();

	protected abstract void setupViews(Bundle bundle);

	protected abstract void setListener();

	protected abstract void fetchData();

	
}
