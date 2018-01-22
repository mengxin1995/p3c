package com.alibaba.p3c.idea.inspection.tuotuo

import com.alibaba.p3c.idea.i18n.P3cBundle
import com.alibaba.p3c.idea.inspection.AliBaseInspection
import com.alibaba.p3c.idea.inspection.tuotuo.base.MissingActivityRouteInspection
import com.alibaba.p3c.idea.util.HighlightDisplayLevels
import com.intellij.codeHighlighting.HighlightDisplayLevel

class TuoMissingAcitvityRouteInspection : MissingActivityRouteInspection, AliBaseInspection {

    private val messageKey = "com.alibaba.p3c.idea.inspection.tuotuo.TuoMissingAcitvityRouteInspection"

    constructor()

    /**
     * For Javassist
     */
    constructor(any: Any?) : this()

    override fun ruleName(): String {
        return "MissingAcitvityRouteRule"
    }

    //错误信息
    override fun buildErrorString(vararg infos: Any): String = P3cBundle.getMessage("$messageKey.errMsg")

    override fun getDisplayName(): String = "Your activity should add route"

    override fun getStaticDescription(): String? = "Your activity should add route !!!"

    override fun getDefaultLevel(): HighlightDisplayLevel = HighlightDisplayLevels.CRITICAL

    override fun getShortName(): String = "TuoMissingAcitvityRoute"

    override fun isEnabledByDefault(): Boolean = true

}