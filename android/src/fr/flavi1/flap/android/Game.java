package fr.flavi1.flap.android;

import android.content.Context;
import android.util.Log;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.flavi1.flap.android.AndroidLauncher;

public class Game extends ApplicationAdapter {

	public static enum State {
		MENU, GAME, BIRDFALL, SCORE_DISPLAY
	}

	InputManager inputManager;
	AndroidLauncher activity;

	SpriteBatch batch;
	ScoreManager scoreManager;

	public static State state;
	Player player;

	OrthographicCamera camera;
	Viewport viewport;

	long startTime;

	public int w;
	public int h;

	public Game(AndroidLauncher act) {
		activity = act;
		h = 800;
		w = 480;
	}

	@Override
	public void create() {
		scoreManager = new ScoreManager(activity);
		TextureManager.load();
		ObstacleManager.load();
		inputManager = new InputManager();
		Gdx.input.setInputProcessor(inputManager);
		scoreManager.load();
		SoundManager.load();

		player = new Player();

		state = State.MENU;

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		viewport = new StretchViewport(480, 800, camera);
		viewport.apply();

		camera.position.set(240, 400, 0);
	}

	@Override
	public void render() {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(TextureManager.backTexure, 0, 0);

		if (state == State.MENU) {
			batch.draw(TextureManager.menuShadow, 0, 0);
			batch.draw(TextureManager.menuOverScreen, 256 - TextureManager.menuOverScreen.getWidth() / 2, 183 - TextureManager.menuOverScreen.getHeight());

			if (inputManager.touched()) {
				setState(State.GAME);
			}
		} else if (state == State.GAME) {
			batch.draw(TextureManager.playerAnimation.getKeyFrame((float) (TimeUtils.millis() - startTime) / 1000f), player.getX(), player.getY());
			player.update(Gdx.graphics.getDeltaTime(), scoreManager);

			if (player.isDead())
				setState(State.BIRDFALL);

			if (inputManager.touched()) {
				player.flip();
			}

			ObstacleManager.update(Gdx.graphics.getDeltaTime());
			ObstacleManager.drawObstacles(batch);

			scoreManager.displayScore(batch);
		} else if (state == State.BIRDFALL) {
			player.update(Gdx.graphics.getDeltaTime(), scoreManager);

			batch.draw(TextureManager.playerSteadyTexture, player.getX(), player.getY(), 0, 0, TextureManager.playerSteadyTexture.getWidth(), TextureManager.playerSteadyTexture.getHeight(), 1f, 1f, player.getRotation(), 0, 0, TextureManager.playerSteadyTexture.getWidth(), TextureManager.playerSteadyTexture.getHeight(), player.isFlipped(), false);

			if (player.isFallen())
				setState(State.SCORE_DISPLAY);

			ObstacleManager.drawObstacles(batch);
			scoreManager.displayScore(batch);
		} else if (state == State.SCORE_DISPLAY) {
			Panel.update(Gdx.graphics.getDeltaTime());
			Panel.draw(batch, scoreManager);

			if (inputManager.touched()) {
				if (Panel.isSteady()) {
					if (CollisionManager.isInRect(480-inputManager.coord[0]*480/w, 800-inputManager.coord[1]*800/h, Panel.getLeaderboardsRect()))
						scoreManager.shareBest();
					else
						setState(State.MENU);
				}
			}

		}

		batch.draw(TextureManager.wallLeftTexture, 0, 0);
		batch.draw(TextureManager.wallRightTexture, 412, 0);
		batch.end();
	}

	void setState(State newState) {
		if (state == newState) return;

		state = newState;

		if (state == State.MENU) {
			player.reset();
			ObstacleManager.reset();
			scoreManager.reset();
		} else if (state == State.GAME) {
			player.start();
			startTime = TimeUtils.millis();
		} else if (state == State.BIRDFALL) {
			SoundManager.fallSound.play();
		} else if (state == State.SCORE_DISPLAY) {
			Panel.reset();
			if (scoreManager.getScore() > scoreManager.readBestScore())
				scoreManager.registerBestScore(scoreManager.getScore());
		}
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		try {
			viewport.update(width, height);
			camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
