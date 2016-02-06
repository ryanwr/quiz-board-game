/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz.state;

import java.awt.*;
import me.ryanwelch.quiz.graphics.*;

public interface IState
{

    void onEnter();

    void onExit();

    void update();

    void draw(Display display, Graphics g);
}
