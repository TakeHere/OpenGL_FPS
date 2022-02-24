package core.animations;

import core.objects.entities.Entity;
import core.toolbox.Vector3;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Animation {

    Entity entity;

    float endValue;
    float currentValue;
    float step;

    Expression positionXFormula;
    Expression positionYFormula;
    Expression positionZFormula;

    Expression rotationXFormula;
    Expression rotationYFormula;
    Expression rotationZFormula;

    Expression scaleXFormula;
    Expression scaleYFormula;
    Expression scaleZFormula;

    public Animation(Entity entity, float startValue, float endValue, float step, String positionXFormula, String positionYFormula, String positionZFormula, String rotationXFormula, String rotationYFormula, String rotationZFormula, String scaleXFormula, String scaleYFormula, String scaleZFormula) {
        this.entity = entity;
        this.endValue = endValue;
        this.step = step;

        this.positionXFormula = new ExpressionBuilder(positionXFormula).variable("x").build();
        this.positionYFormula = new ExpressionBuilder(positionYFormula).variable("x").build();
        this.positionZFormula = new ExpressionBuilder(positionZFormula).variable("x").build();

        this.rotationXFormula = new ExpressionBuilder(rotationXFormula).variable("x").build();
        this.rotationYFormula = new ExpressionBuilder(rotationYFormula).variable("x").build();
        this.rotationZFormula = new ExpressionBuilder(rotationZFormula).variable("x").build();

        this.scaleXFormula = new ExpressionBuilder(scaleXFormula).variable("x").build();
        this.scaleYFormula = new ExpressionBuilder(scaleYFormula).variable("x").build();
        this.scaleZFormula = new ExpressionBuilder(scaleZFormula).variable("x").build();

        currentValue = startValue;

        AnimationsController.addAnimation(this);
    }

    public void animate(){
        currentValue+=step;

        positionXFormula.setVariable("x", currentValue);
        positionYFormula.setVariable("x", currentValue);
        positionZFormula.setVariable("x", currentValue);

        rotationXFormula.setVariable("x", currentValue);
        rotationYFormula.setVariable("x", currentValue);
        rotationZFormula.setVariable("x", currentValue);

        scaleXFormula.setVariable("x", currentValue);
        scaleYFormula.setVariable("x", currentValue);
        scaleZFormula.setVariable("x", currentValue);

        entity.setPosition(new Vector3(
                (float) positionXFormula.evaluate(),
                (float) positionYFormula.evaluate(),
                (float) positionZFormula.evaluate()
        ));

        entity.setRotation(new Vector3(
                (float) rotationXFormula.evaluate(),
                (float) rotationYFormula.evaluate(),
                (float) rotationZFormula.evaluate()
        ));

        entity.setScale(new Vector3(
                (float) scaleXFormula.evaluate(),
                (float) scaleYFormula.evaluate(),
                (float) scaleZFormula.evaluate()
        ));

        if (currentValue >= endValue){
            AnimationsController.removeAnimation(this);
        }
    }
}
