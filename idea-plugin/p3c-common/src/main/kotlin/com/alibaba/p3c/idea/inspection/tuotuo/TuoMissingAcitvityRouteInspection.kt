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

/*
* 这边要继承BaseInspection,AliBaseInspection
* BaseInspection是IDEA源码里面的基础类
* AliBaseInspection是阿里提供的基础类
* 这两个类是一定要实现的
* */
class TuoMissingAcitvityRouteInspection : BaseInspection, AliBaseInspection {

    private val messageKey = "com.alibaba.p3c.idea.inspection.tuotuo.TuoMissingAcitvityRouteInspection"

    constructor()

    /**
     * For Javassist
     */
    constructor(any: Any?) : this()

    //规程的标题
    override fun ruleName(): String {
        return "MissingAcitvityRouteRule"
    }

    //错误信息
    override fun buildErrorString(vararg infos: Any): String = P3cBundle.getMessage("$messageKey.errMsg")

    //错误提示标题
    override fun getDisplayName(): String = "Your activity should add route"

    //错误提示内容体
    override fun getStaticDescription(): String? = "Your activity should add route !!!"

    //错误的等级
    override fun getDefaultLevel(): HighlightDisplayLevel = HighlightDisplayLevels.CRITICAL

    //规则的短标题
    override fun getShortName(): String = "TuoMissingAcitvityRoute"

    //是否默认开启
    override fun isEnabledByDefault(): Boolean = true

    //回调一个遍历的类，这个类是由IDEA自己去控制的
    override fun buildVisitor(): BaseInspectionVisitor {
        return MissingActivityRouteVisitor()
    }

    /*
    * 这里是规则的具体写法，比较灵活，自己的业务可以看一下别的规则的实现来推导自己的实现
    * 我这里只要是如果这个类名字里面包含了Activity，就去拿它的注解，
    * 如果注解为空，或者说注解列表里面不包含"com.alibaba.android.arouter.facade.annotation.Route"
    * 即表示没有添加过路由，就会通过registerClassError(aClass)输出错误信息
    * */
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

}