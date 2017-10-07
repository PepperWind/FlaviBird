package fr.flavi1.flap.android;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, ActionResolver{

	private GameHelper gameHelper;

	private static final int RC_RESOLVE = 5000;
	private static final int RC_UNUSED = 5001;
	private static final int RC_SIGN_IN = 9001;

	public  GoogleApiClient mGoogleApiClient;

    /*public void showLeaderboards() {
        Log.d("Signed in :", ""+isSignedIn());

        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            mGoogleApiClient.connect();
            if(!isSignedIn()) {
                Toast.makeText(this, "Leaderboards not available", Toast.LENGTH_SHORT).show();
                Log.d("Leaderboards not available", "Leaderboards not available");
            }

            else
                startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                        RC_UNUSED);
        }
    }*/

    /*public void onResizeWQuery320(int s) {
        if (!isSignedIn()) {
            mGoogleApiClient.connect();
        }

        if(!isSignedIn())
            return;
        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_score),
                s);
    }*/



	private boolean isSignedIn() {
		return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
	}

    /*@Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Flavi-1", "onConnectionSuspended() called. Trying to reconnect.");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        try {
            connectionResult.startResolutionForResult(this, RC_SIGN_IN);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }*/

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);
		Log.d("Flavi-1", "0");
		gameHelper.setup(this);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useWakelock = true;
		initialize(new Game(this), config);
		onTempStop();
		onTempRestart();

        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();*/
	}

	public void onTempStop(){
		super.onStart();
	}

	public void onTempRestart(){
		super.onRestart();
	}


	@Override
	public void onStart(){
		Log.d("Flavi-1", "1");
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		if (!getSignedInGPGS()) loginGPGS();
		if (!getSignedInGPGS()) return;
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIzbD-o4AeEAIQAA", score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {
		if (!getSignedInGPGS()) loginGPGS();
		if (!getSignedInGPGS()) return;
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIzbD-o4AeEAIQAA"), 100);
	}

	@Override
	public void getAchievementsGPGS() {
		//startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}
}
