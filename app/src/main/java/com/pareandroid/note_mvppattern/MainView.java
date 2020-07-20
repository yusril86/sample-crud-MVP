package com.pareandroid.note_mvppattern;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideloading();
    void onGetResult(List<Notes> notes);
    void onErrorLoading(String message);
}
