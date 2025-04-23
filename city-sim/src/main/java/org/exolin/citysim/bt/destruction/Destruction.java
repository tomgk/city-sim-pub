package org.exolin.citysim.bt.destruction;

import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class Destruction
{
    public static final FireType fire = new FireType("fire", createAnimation("destruction/fire", 4, 500), 1);
}
