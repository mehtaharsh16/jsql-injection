/*******************************************************************************
 * Copyhacked (H) 2012-2020.
 * This program and the accompanying materials
 * are made available under no term at all, use it like
 * you want, but share and discuss about it
 * every time possible with every body.
 *
 * Contributors:
 *      ron190 at ymail dot com - initial implementation
 *******************************************************************************/
package com.jsql.model.injection.strategy;

import com.jsql.model.InjectionModel;
import com.jsql.model.bean.util.Interaction;
import com.jsql.model.bean.util.Request;
import com.jsql.model.exception.StoppedByUserSlidingException;
import com.jsql.model.injection.strategy.blind.AbstractInjectionBoolean.BooleanMode;
import com.jsql.model.injection.strategy.blind.InjectionTime;
import com.jsql.model.injection.vendor.model.VendorYaml;
import com.jsql.model.suspendable.AbstractSuspendable;
import com.jsql.util.I18nUtil;
import com.jsql.util.LogLevelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrategyInjectionTime extends AbstractStrategy {
    
    /**
     * Log4j logger sent to view.
     */
    private static final Logger LOGGER = LogManager.getRootLogger();

    private InjectionTime injectionTime;
    
    public StrategyInjectionTime(InjectionModel injectionModel) {
        
        super(injectionModel);
    }

    @Override
    public void checkApplicability() throws StoppedByUserSlidingException {

        if (this.injectionModel.getMediatorUtils().getPreferencesUtil().isStrategyTimeDisabled()) {

            LOGGER.log(LogLevelUtil.CONSOLE_INFORM, "Skipping strategy Time disabled");
            return;

        } else if (StringUtils.isEmpty(this.injectionModel.getMediatorVendor().getVendor().instance().sqlBooleanTime())) {

            LOGGER.log(LogLevelUtil.CONSOLE_INFORM, "No Time strategy known for {}", this.injectionModel.getMediatorVendor().getVendor());
            return;

        }

        checkInjection(BooleanMode.STACKED);
        checkInjection(BooleanMode.NO_MODE);
        checkInjection(BooleanMode.OR);
        checkInjection(BooleanMode.AND);

        if (this.isApplicable) {

            this.allow();

            var requestMessageBinary = new Request();
            requestMessageBinary.setMessage(Interaction.MESSAGE_BINARY);
            requestMessageBinary.setParameters(this.injectionTime.getInfoMessage());
            this.injectionModel.sendToViews(requestMessageBinary);

        } else {

            this.unallow();
        }
    }

    private void checkInjection(BooleanMode booleanMode) throws StoppedByUserSlidingException {

        if (this.isApplicable) {
            return;
        }

        LOGGER.log(
            LogLevelUtil.CONSOLE_DEFAULT,
            "{} Time with {}...",
            () -> I18nUtil.valueByKey(KEY_LOG_CHECKING_STRATEGY),
            () -> booleanMode
        );
        this.injectionTime = new InjectionTime(this.injectionModel, booleanMode);
        this.isApplicable = this.injectionTime.isInjectable();

        if (this.isApplicable) {
            LOGGER.log(
                LogLevelUtil.CONSOLE_SUCCESS,
                "{} Time injection with {}",
                () -> I18nUtil.valueByKey(KEY_LOG_VULNERABLE),
                () -> booleanMode
            );
        }
    }
    
    @Override
    public void allow(int... i) {
        
        this.markVulnerability(Interaction.MARK_TIME_VULNERABLE);
    }

    @Override
    public void unallow(int... i) {
        
        this.markVulnerability(Interaction.MARK_TIME_INVULNERABLE);
    }

    @Override
    public String inject(String sqlQuery, String startPosition, AbstractSuspendable stoppable, String metadataInjectionProcess) throws StoppedByUserSlidingException {
        
        return this.injectionTime.inject(
            this.injectionModel.getMediatorVendor().getVendor().instance().sqlTime(sqlQuery, startPosition),
            stoppable
        );
    }

    @Override
    public void activateStrategy() {
        
        LOGGER.log(
            LogLevelUtil.CONSOLE_INFORM,
            "{} [{}] with [{}]",
            () -> I18nUtil.valueByKey("LOG_USING_STRATEGY"),
            this::getName,
            () -> this.injectionTime.getBooleanMode().name()
        );
        this.injectionModel.getMediatorStrategy().setStrategy(this.injectionModel.getMediatorStrategy().getTime());
        
        var requestMarkTimeStrategy = new Request();
        requestMarkTimeStrategy.setMessage(Interaction.MARK_TIME_STRATEGY);
        this.injectionModel.sendToViews(requestMarkTimeStrategy);
    }
    
    @Override
    public String getPerformanceLength() {
        return VendorYaml.DEFAULT_CAPACITY;
    }
    
    @Override
    public String getName() {
        return "Time";
    }
}
