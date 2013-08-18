package info.samratcliff.ui.model;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import info.samratcliff.core.Problem.ProblemManager;
import info.samratcliff.core.Problem.Util.ProblemTag;
import info.samratcliff.core.Problem.quantumproblem;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class QuantumProblemProperty implements Property<quantumproblem> {

    List<ChangeListener<? super quantumproblem>> changeListeners = Lists
            .newArrayList();
    List<InvalidationListener> invalidationListeners = Lists.newArrayList();

    private final ProblemManager pm;
    private quantumproblem qp;
    private ProblemTag qpt;
    private final StringProperty nameProperty;
    private final StringProperty descriptionProperty;

    @Inject
    public QuantumProblemProperty(ProblemManager problemManager) {
        this.pm = problemManager;
        nameProperty = new SimpleStringProperty();
        descriptionProperty = new SimpleStringProperty();
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public quantumproblem getValue() {
        return qp;
    }

    @Override
    public void setValue(quantumproblem arg0) {
        final quantumproblem oldqp = qp;
        qp = arg0;
        if (qp != null) {
            qpt = pm.getProblemTag(qp.getName());
            nameProperty.setValue(qp.getName());
            descriptionProperty.setValue(qpt.Desc);
            for (ChangeListener<? super quantumproblem> cl : changeListeners) {
                cl.changed(this, oldqp, qp);
            }
        } else {
            for (InvalidationListener il : invalidationListeners) {
                il.invalidated(this);
            }
        }
    }

    @Override
    public void addListener(ChangeListener<? super quantumproblem> arg0) {
        changeListeners.add(arg0);
    }

    @Override
    public void removeListener(ChangeListener<? super quantumproblem> arg0) {
        changeListeners.remove(arg0);
    }

    @Override
    public void addListener(InvalidationListener arg0) {
        invalidationListeners.add(arg0);
    }

    @Override
    public void removeListener(InvalidationListener arg0) {
        invalidationListeners.remove(arg0);
    }

    @Override
    public void bind(ObservableValue<? extends quantumproblem> arg0) {

    }

    @Override
    public void bindBidirectional(Property<quantumproblem> arg0) {

    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public void unbind() {

    }

    @Override
    public void unbindBidirectional(Property<quantumproblem> arg0) {

    }

}
