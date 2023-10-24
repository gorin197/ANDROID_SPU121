package step.learning.android_spu121;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity  {
    private static final int N = 4;
    private final Random random = new Random();
    private  int [][] cells= new int[N][N];
    private TextView[][] tvCells=new TextView [N][N];
    private  int score;
    int bestScore ;
    private TextView tvScore;
    private  TextView tvBestScore;
    private Animation spanCellAnimation;
    private Animation collapseCellAnimation;
    private MediaPlayer spawnSound;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
if(event.getAction()==KeyEvent.ACTION_DOWN){
    switch (keyCode){
        case KeyEvent.KEYCODE_DPAD_UP:
            processMove(MoveDirection.TOP);
            return true;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            processMove(MoveDirection.BOTTOM);
            return true;
        case KeyEvent.KEYCODE_DPAD_LEFT:
            processMove(MoveDirection.LEFT);
            return true;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            processMove(MoveDirection.RIGHT);
            return true;
    }
}

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_game );
        spawnSound=MediaPlayer.create(GameActivity.this,R.raw.pickup_00);
        spanCellAnimation= AnimationUtils.loadAnimation(GameActivity.this,R.anim.game_spawn_cell);
        spanCellAnimation.reset();
        collapseCellAnimation= AnimationUtils.loadAnimation(GameActivity.this,R.anim.game_spawn_cell);
        collapseCellAnimation.reset();
        tvScore=findViewById(R.id.game_tv_score);
        tvBestScore= findViewById(R.id.game_tv_best);

        // пошук ідентифікаторів за іменем (String) та ресурсів через них
        for (int i = 0; i <N ; i++) {
            for (int j = 0; j <N ; j++) {
             tvCells[i][j] = findViewById(getResources().getIdentifier("game_cell_"+i+j,"id",getPackageName())) ;

            }
        }

        // Задати ігровому полю висоту таку ж як ширину
        // Проблема: на етапі onCreate розміри ще не відомі
        TableLayout gameField = findViewById( R.id.game_field ) ;
        gameField.post(   // поставити задачу у чергу, вона буде виконана
                // коли gameField виконає усі попередні задачі, у т.ч.
                // розрахунок розміру та рисування.
                () -> {
                    int windowWidth = this.getWindow().getDecorView().getWidth() ;
                    int margin =
                            ((LinearLayout.LayoutParams)gameField.getLayoutParams()).leftMargin;
                    int fieldSize = windowWidth - 2 * margin ;
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams( fieldSize, fieldSize ) ;
                    layoutParams.setMargins( margin, margin, margin, margin );
                    gameField.setLayoutParams( layoutParams ) ;
                }
        ) ;

        findViewById(R.id.game_layout).setOnTouchListener(
        new OnSwipeListener(GameActivity.this){
            @Override public void onSwipeBottom() {
                processMove(MoveDirection.BOTTOM);
//                Toast.makeText(   // повідомлення, що з'являється та зникає
//                        GameActivity.this,   // контекст
//                        "onSwipeBottom",     // повідомлення
//                        Toast.LENGTH_SHORT   // тривалість (довжина у часі)
//                ).show();
            }

            @Override
            public void onSwipeLeft() {
                processMove(MoveDirection.LEFT);
            }

            @Override
            public void onSwipeRight() {
                processMove(MoveDirection.RIGHT);
            }

            @Override
            public void onSwipeTop() {
                processMove(MoveDirection.TOP);
            }
        }



        );
        newGame();
    }

    private void newGame(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j]=0;

            }

        }
        spanCell();
        spanCell();
        showField();
    }
    /**
     * Поставити нове число у випадкову вільну комірку.
     * Значення: 2 (з імовірністью 0.9) або 4
     */

    private void spanCell(){
        // шукаємо всі вільні комірки
        List<Integer> freeCellsIndexes=new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(cells[i][j]==0) {
                    freeCellsIndexes.add(i*N+j);
                }
            }
        }
        int randIndex=freeCellsIndexes.get(random.nextInt(freeCellsIndexes.size()));
        int x=randIndex/N;
        int y=randIndex%N;
        // заповнюємо комірку значенням
        cells[x][y]=random.nextInt(10)==0 ?4:2;// умова з імов. 0.1
        // призначаємо анімацію для її представлення
        tvCells[x][y].startAnimation(spanCellAnimation);
        // програємо звук
        spawnSound.start();

    }
    /**
     * Показ усіх комірок - відображення масиву чисел на ігрове поле
     */
    private void showField(){
        // для кожної комірки визначаємо стиль у відповідності до
        // її значення та застосовуємо його.
        Resources resources=getResources();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // сам текст комірки
                tvCells[i][j].setText(String.valueOf(cells[i][j]));
                // стиль !! але зі стилем застосовуються не всі атрибути
                tvCells[i][j].setTextAppearance(resources.getIdentifier("game_cell_" + cells[i][j],"style",getPackageName()));
                // фонові атрибути потрібно додати окремо (до стилів)
                tvCells[i][j].setBackgroundColor(resources.getColor(resources.getIdentifier("game_cell_background_"+cells[i][j],"color",getPackageName())));  // R.color

            }
            getTheme();
        }
        // виводимо рахунок та кращий (заповнюючи плейсхолдер ресурсу)
        tvScore.setText(getString(R.string.game_tv_score,score));
        tvBestScore.setText(getString(R.string.game_tv_best,bestScore));
    }
    private  boolean canMoveLeft(){
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N-1; j++) {
            if(cells[i][j]==0 && cells[i][j+1]!=0 ||
                    cells[i][j+1]!=0&&cells[i][j+1]==cells[i][j+1]){
                return  true;
            }
        }
    }
        
return  false;
}

    private void moveLeft() {
        //                                                        [2028]
        // 1. Переміщуємо всі значення ліворуч (без "пробілів")   [2280]
        // 2. Перевіряємо коллапси (злиття), зливаємо             [4-80]
        // 3. Повторюємо п. 1 після злиття                        [4800]
        // 4. Повторюємо пп.1-3 для кожного рядка
        for( int i = 0; i < N; i++ ) {
            // 1. ліворуч
            boolean needRepeat ;
            do {
                needRepeat = false ;
                for( int j = 0; j < N - 1; j++ ) {
                    if( cells[ i ][ j ] == 0 && cells[ i ][ j + 1 ] != 0 ) {
                        cells[ i ][ j ] = cells[ i ][ j + 1 ];
                        cells[ i ][ j + 1 ] = 0;
                        needRepeat = true;
                    }
                }
            } while( needRepeat ) ;

            // 2. коллапс
            for( int j = 0; j < N - 1; j++ ) {
                if( cells[ i ][ j ] != 0
                        && cells[ i ][ j ] == cells[ i ][ j + 1 ]    // [2284]
                ) {
                    cells[ i ][ j ] += cells[ i ][ j + 1 ] ;  // [4284]
                    score += cells[ i ][ j ] ;
                    tvCells[ i ][ j ].startAnimation( collapseCellAnimation ) ;
                    // 3. Переміщуємо на "злите" місце
                    for( int k = j + 1; k < N - 1; k++ ) {    // [4844]
                        cells[ i ][ k ] = cells[ i ][ k + 1 ] ;
                    }
                    // занулюємо останню                         [4840]
                    cells[ i ][ N - 1 ] = 0 ;
                }
            }
        }
    }

    private boolean canMoveRight() {
        for( int i = 0; i < N; i++ ) {
            for( int j = 1; j < N ; j++ ) {
                if( cells[ i ][ j ] == 0 && cells[ i ][ j - 1 ] != 0 ||
                        cells[ i ][ j ] != 0 && cells[ i ][ j ] == cells[ i ][ j - 1 ] ) {
                    return true ;
                }
            }
        }
        return false ;
    }
    private void moveRight() {
        for( int i = 0; i < N; i++ ) {
            // 1. праворуч
            boolean needRepeat ;
            do {
                needRepeat = false ;
                for( int j = N - 1; j > 0; j-- ) {
                    if( cells[ i ][ j ] == 0 && cells[ i ][ j - 1 ] != 0 ) {
                        cells[ i ][ j ] = cells[ i ][ j - 1 ];
                        cells[ i ][ j - 1 ] = 0;
                        needRepeat = true;
                    }
                }
            } while( needRepeat ) ;

            // 2. коллапс
            for( int j = N - 1; j > 0; j-- ) {
                if( cells[ i ][ j ] != 0
                        && cells[ i ][ j ] == cells[ i ][ j - 1 ]    // [4222]
                ) {
                    cells[ i ][ j ] += cells[ i ][ j - 1 ] ;         // [4224]
                    score += cells[ i ][ j ] ;
                    tvCells[ i ][ j ].startAnimation( collapseCellAnimation ) ;
                    // 3. Переміщуємо на "злите" місце
                    for( int k = j - 1; k > 0; k-- ) {
                        cells[ i ][ k ] = cells[ i ][ k - 1 ] ;
                    }
                    // занулюємо першу                              [0424]
                    cells[ i ][ 0 ] = 0 ;
                }
            }
        }
    }

    private boolean canMoveUp() {
        for (int j = 0; j < N; j++) {
            for (int i = 1; i < N; i++) {
                if (cells[i][j] != 0 && (cells[i - 1][j] == 0 || cells[i - 1][j] == cells[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveDown() {
        for (int j = 0; j < N; j++) {
            for (int i = N - 2; i >= 0; i--) {
                if (cells[i][j] != 0 && (cells[i + 1][j] == 0 || cells[i + 1][j] == cells[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void moveUp() {
        for (int j = 0; j < N; j++) {
            boolean needRepeat;
            do {
                needRepeat = false;
                for (int i = 0; i < N - 1; i++) {
                    if (cells[i][j] == 0 && cells[i + 1][j] != 0) {
                        cells[i][j] = cells[i + 1][j];
                        cells[i + 1][j] = 0;
                        needRepeat = true;
                    }
                }
            } while (needRepeat);

            for (int i = 0; i < N - 1; i++) {
                if (cells[i][j] != 0 && cells[i][j] == cells[i + 1][j]) {
                    cells[i][j] += cells[i + 1][j];
                    score += cells[i][j];
                    tvCells[i][j].startAnimation(collapseCellAnimation);

                    for (int k = i + 1; k < N - 1; k++) {
                        cells[k][j] = cells[k + 1][j];
                    }
                    cells[N - 1][j] = 0;
                }
            }
        }
    }

    private void moveDown() {
        for (int j = 0; j < N; j++) {
            boolean needRepeat;
            do {
                needRepeat = false;
                for (int i = N - 1; i > 0; i--) {
                    if (cells[i][j] == 0 && cells[i - 1][j] != 0) {
                        cells[i][j] = cells[i - 1][j];
                        cells[i - 1][j] = 0;
                        needRepeat = true;
                    }
                }
            } while (needRepeat);

            for (int i = N - 1; i > 0; i--) {
                if (cells[i][j] != 0 && cells[i][j] == cells[i - 1][j]) {
                    cells[i][j] += cells[i - 1][j];
                    score += cells[i][j];
                    tvCells[i][j].startAnimation(collapseCellAnimation);

                    for (int k = i - 1; k > 0; k--) {
                        cells[k][j] = cells[k - 1][j];
                    }
                    cells[0][j] = 0;
                }
            }
        }
    }

    private boolean canMove(MoveDirection direction){
        switch (direction){

            case BOTTOM: return canMoveDown();
            case LEFT:return canMoveLeft();
            case RIGHT:return canMoveRight();
            case TOP:return  canMoveUp();
        }
        return  false;
    }

    private void move( MoveDirection direction ) {
        switch( direction ) {
            case BOTTOM:moveDown(); break;
            case LEFT: moveLeft(); break;
            case RIGHT: moveRight(); break;
            case TOP: moveUp(); break;
        }
    }


    private enum MoveDirection{
        BOTTOM,LEFT,RIGHT,TOP
    }

    private  void processMove(MoveDirection direction){
        if(canMove(direction)){
            move(direction);
            spanCell();
            showField();
            checkGameState();
        }
//        else { //Проверяем на проигрыш
//            Toast.makeText( GameActivity.this, "НЕТ ХОДА!", Toast.LENGTH_SHORT ).show();
//        }
    }
        //Проверяем на выиграш
    private boolean checkWin() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cells[i][j] == 2048) {
                    return true; // Выиграш, достигнуто число 2048
                }
            }
        }
        return false; //Продожаем играть
    }

    //Проверяем на проигрыш
    private boolean checkLose() {
        if (canMoveLeft() || canMoveRight() || canMoveUp() || canMoveDown()) {
            return false; // Больше нет ходов
        }
        Toast.makeText( GameActivity.this, "GAME OVER!", Toast.LENGTH_SHORT ).show();
        return true;
    }

    private void checkGameState() {
        if (checkWin()) {
            // Виграш
            showGameOverDialog("You win!", "Вы достигли числчёа 2048!");
        } else if (checkLose()) {
            // Програш
            showGameOverDialog("GAME OVER!", "Больше бежать некуда!>");
        }
    }

    private void showGameOverDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Нова гра", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newGame();
                    }
                })
                .setCancelable(false)
                .show();
    }
}




/*
Д.З. Завершити проєкт 2048
- Додати рухи вгору та вниз
- Реалізувати кінець гри (AlertDialog):
 = неможливість рухів - програш
 = набір 2048 хоча б в одній комірці - виграш
   - тут можна реалізувати вибір продовження гри
     до програшу без повторних повідомлень про виграш
- Рекорд та його збереження (на постійній основі - у файл)
* Кнопка "назад" - повернення попереднього стану (рух назад)
 - відновлення стану поля
 - повернення рахунку
 - перевірка рекорду
- Заблокувати поворот пристрою або
 * зробити ландшафтну орієнтацію
- Додати звуків та засобів управління ними (гучність або вимикач)
 */