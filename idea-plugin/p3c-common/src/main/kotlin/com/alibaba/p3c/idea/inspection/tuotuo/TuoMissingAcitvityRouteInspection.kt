package com.alibaba.p3c.idea.inspection.tuotuo

import com.alibaba.p3c.idea.i18n.P3cBundle
import com.alibaba.p3c.idea.inspection.AliBaseInspection
import com.alibaba.p3c.idea.util.HighlightDisplayLevels
import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiModifierListOwner
import com.siyeh.ig.BaseInspection
import com.siyeh.ig.BaseInspectionVisitor
import org.apache.http.util.TextUtils
import org.intellij.plugins.relaxNG.compact.psi.RncElementVisitor

class TuoMissingAcitvityRouteInspection : BaseInspection, AliBaseInspection {
    override fun buildVisitor(): BaseInspectionVisitor {
        return MissingActivityRouteVisitor()
    }

    class MissingActivityRouteVisitor : BaseInspectionVisitor() {
        override fun visitClass(aClass: PsiClass?) {
            super.visitClass(aClass)
            if (aClass == null) return

            if (TextUtils.isEmpty(aClass.name) || !aClass.name!!.contains("Activity")) {
                return
            }

            if (hasRouteOverrideAnnotation(aClass)) {
                return
            }
            registerClassError(aClass)
        }

        private fun hasRouteOverrideAnnotation(
                element: PsiModifierListOwner): Boolean {
            val modifierList = element.modifierList ?: return false
            val annotation = modifierList.findAnnotation("com.alibaba.android.arouter.facade.annotation.Route")
            return annotation != null
        }
    }


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