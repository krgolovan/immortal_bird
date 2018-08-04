package com.kirill_golovan.immortalflappybird;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.Interpolation.circle;

public class ScrollHandler {

    private Grass frontGrass, backGrass;
    private Dirt frontDirt, backDirt;
    private Pipe pipe1, pipe2, pipe3;
    private Circle birdCircle;
    public Rectangle botPipe1, upPipe1, botPipe2, upPipe2, botPipe3, upPipe3;
    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;

    private GameWorld gameWorld;

    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);
        frontDirt = new Dirt(0,yPos+11,143,175,SCROLL_SPEED);
        backDirt = new Dirt(frontDirt.getTailX(),yPos+11,143,175,SCROLL_SPEED);

        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);


        birdCircle = new Circle(gameWorld.getBird().getX()+gameWorld.getBird().getWidth()/2,gameWorld.getBird().getY()+gameWorld.getBird().getHeight()/2,gameWorld.getBird().getHeight()/2);
        upPipe1 = new Rectangle(pipe1.getX(), 0, pipe1.getWidth(), pipe1.getHeight());
        botPipe1 = new Rectangle(pipe1.getX(), pipe1.getGroundY(), pipe1.getWidth(), pipe1.getHeight() + 45 - pipe1.getGroundY());
        upPipe2 = new Rectangle(pipe2.getX(), 0, pipe2.getWidth(), pipe2.getHeight());
        botPipe2 = new Rectangle(pipe2.getX(), pipe2.getGroundY(), pipe2.getWidth(), pipe2.getHeight() + 45 - pipe2.getGroundY());
        upPipe3 = new Rectangle(pipe3.getX(), 0, pipe3.getWidth(), pipe3.getHeight());
        botPipe3 = new Rectangle(pipe3.getX(), pipe3.getGroundY(), pipe3.getWidth(), pipe3.getHeight() + 45 - pipe3.getGroundY());

    }

    public void updateReady(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);

        frontDirt.update(delta);
        backDirt.update(delta);

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());

        }

        if (frontDirt.isScrolledLeft()) {
            frontDirt.reset(backDirt.getTailX());

        } else if (backDirt.isScrolledLeft()) {
            backDirt.reset(frontDirt.getTailX());
        }

    }

    public void update(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);

        frontDirt.update(delta);
        backDirt.update(delta);

        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);

        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }

        if (frontDirt.isScrolledLeft()) {
            frontDirt.reset(backDirt.getTailX());

        } else if (backDirt.isScrolledLeft()) {
            backDirt.reset(frontDirt.getTailX());
        }

        birdCircle.set(gameWorld.getBird().getX()+gameWorld.getBird().getWidth()/2,gameWorld.getBird().getY()+gameWorld.getBird().getHeight()/2,gameWorld.getBird().getHeight()/2);
        upPipe1.set(pipe1.getX(), 0, pipe1.getWidth(), pipe1.getHeight());
        botPipe1.set(pipe1.getX(), 0 + pipe1.getHeight()+45, pipe1.getWidth(), -(pipe1.getHeight() + 45 - pipe1.getGroundY()));
        upPipe2.set(pipe2.getX(), 0, pipe2.getWidth(), pipe2.getHeight());
        botPipe2.set(pipe2.getX(), 0 + pipe2.getHeight()+45, pipe2.getWidth(), -(pipe2.getHeight() + 45 - pipe2.getGroundY()));
        upPipe3.set(pipe3.getX(), 0, pipe3.getWidth(), pipe3.getHeight());
        botPipe3.set(pipe3.getX(), 0 + pipe3.getHeight()+45, pipe3.getWidth(), -(pipe3.getHeight() + 45 - pipe3.getGroundY()));
    }

    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        frontDirt.stop();
        backDirt.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    public boolean collides(Bird bird) {

        if (!pipe1.isScored() && (Intersector.overlaps(birdCircle,botPipe1) || Intersector.overlaps(birdCircle,upPipe1))){
            pipe1.setScored(true);
        }
        if (!pipe2.isScored() && (Intersector.overlaps(birdCircle,botPipe2) || Intersector.overlaps(birdCircle,upPipe2))){
            pipe2.setScored(true);
        }
        if (!pipe3.isScored() && (Intersector.overlaps(birdCircle,botPipe3) || Intersector.overlaps(birdCircle,upPipe3))){
            pipe3.setScored(true);
        }

        if (!pipe1.isScored()  && (pipe1.getX() + (pipe1.getWidth() / 2) - bird.getX() + bird.getHeight()/2  > -4) && (pipe1.getX() + (pipe1.getWidth() / 4) - bird.getX()+bird.getHeight()/2 < -1) &&
                bird.getY() > pipe1.getHeight() && bird.getY() < pipe1.getHeight() + 45) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play();

        } else if (!pipe2.isScored() && (pipe2.getX() + (pipe2.getWidth() / 2) - bird.getX() + bird.getHeight()/2 > -4) && (pipe2.getX() + (pipe2.getWidth() / 4) - bird.getX()+bird.getHeight()/2 < -1) &&
                bird.getY() > pipe2.getHeight() && bird.getY() < pipe2.getHeight() + 45) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play();

        } else if (!pipe3.isScored() && (pipe3.getX() + (pipe3.getWidth() / 2) - bird.getX() + bird.getHeight()/2 > -4) && (pipe3.getX() + (pipe3.getWidth() / 4) - bird.getX()+bird.getHeight()/2 < -1) &&
                bird.getY() > pipe3.getHeight() && bird.getY() < pipe3.getHeight() + 45) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play();
        }

        return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Dirt getFrontDirt() {
        return frontDirt;
    }

    public Dirt getBackDirt() {
        return backDirt;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }

    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        frontDirt.onRestart(0,SCROLL_SPEED);
        backDirt.onRestart(frontDirt.getTailX(),SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }

}