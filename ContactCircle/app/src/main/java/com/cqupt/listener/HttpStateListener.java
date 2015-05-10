package com.cqupt.listener;

/**
 * Created by ls on 15-4-22.
 */
public interface HttpStateListener {

    void postState(String loginState);

    void refreshArticleState(String refreshState);


}
