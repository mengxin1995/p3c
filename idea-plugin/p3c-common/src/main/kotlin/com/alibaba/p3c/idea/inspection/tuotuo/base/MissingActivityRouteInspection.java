package com.alibaba.p3c.idea.inspection.tuotuo.base;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class MissingActivityRouteInspection extends BaseInspection {

    @NotNull
    @Override
    public String getID() {
        return "MissingActivityRoute";
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        return "";
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new MissingActivityRouteVisitor();
    }

    public static class MissingActivityRouteVisitor extends BaseInspectionVisitor{
        @Override
        public void visitClass(PsiClass aClass) {
            super.visitClass(aClass);
            if(!aClass.getName().contains("Activity")){
                return;
            }
            if(hasRouteOverrideAnnotation(aClass)){
                return;
            }
            registerClassError(aClass);
        }

        private boolean hasRouteOverrideAnnotation(
                PsiModifierListOwner element) {
            final PsiModifierList modifierList = element.getModifierList();
            if (modifierList == null) {
                return false;
            }
            final PsiAnnotation annotation =
                    modifierList.findAnnotation("com.alibaba.android.arouter.facade.annotation.Route");
            return annotation != null;
        }
    }
}
