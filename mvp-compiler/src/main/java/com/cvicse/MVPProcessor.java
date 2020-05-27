package com.cvicse;

import com.cvicse.annotations.MVPInject;
import com.cvicse.annotations.MVProvides;
import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.ClassName.bestGuess;

/**
 *
 */
@AutoService(Processor.class)
public final class MVPProcessor extends AbstractProcessor{

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private static final ClassName VIEW_BINDER = ClassName.get("com.cvicse.inject", "MvpBinder");

    private static final String BINDING_CLASS_SUFFIX = "_MvpBinder";//生成类的后缀 以后会用反射去取

    private HashMap<String,String> stringClassHashMap = new HashMap<>();
    @Override public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        filer = env.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(MVPInject.class.getCanonicalName());
        return types;
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    String name;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeElement, List<FieldMVPBinding>> targetClassMap = new LinkedHashMap<>();
        for (Element element: roundEnv.getElementsAnnotatedWith(MVProvides.class)){
            if(element.getKind() == ElementKind.INTERFACE){
                TypeElement typeElement = (TypeElement) element;
                String name  = typeElement.toString();
                List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
                for (AnnotationMirror annotationMirror : annotationMirrors) {

                    // Get the ExecutableElement:AnnotationValue pairs from the annotation element
                    Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                            = annotationMirror.getElementValues();
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                            : elementValues.entrySet()) {
                        //String key = entry.getKey().getSimpleName().toString();
                        Object value = entry.getValue().getValue();
                        //switch (key) {
                            /*case "intValue":
                                int intVal = (Integer) value;
                                System.out.printf(">> intValue: %d\n", intVal);
                                break;
                            case "stringValue":
                                String strVal = (String) value;
                                System.out.printf(">> stringValue: %s\n", strVal);
                                break;
                            case "enumValue":
                                VariableElement enumVal = ((VariableElement) value);
                                System.out.printf(">> enumValue: %s\n", enumVal.getSimpleName());
                                break;
                            case "annotationTypeValue":
                                AnnotationMirror anoTypeVal = (AnnotationMirror) value;
                                System.out.printf(">> annotationTypeValue: %s\n", anoTypeVal.toString());
                                break;*/
                            //case "classValue":
                                TypeMirror typeMirror = (TypeMirror) value;
                               // System.out.printf(">> classValue: %s\n", name = typeMirror.toString());
                                stringClassHashMap.put(name,typeMirror.toString()+".class");
                              //  break;
                           /* case "classesValue":
                                List<? extends AnnotationValue> typeMirrors
                                        = (List<? extends AnnotationValue>) value;
                                System.out.printf(">> classesValue: %s\n",
                                        ((TypeMirror) typeMirrors.get(0).getValue()).toString());
                                break;*/
                        //}
                    }
                }
                //AnnotationValue = typeElement.getAnnotationMirrors(MVProvides.class);
                //mvpProvide.value();
                //name = ((TypeElement) element).getSuperclass().getClass()
               // String packageName = getPackageName(typeElement);
              //  Class _class = typeElement.getAnnotation(MVProvides.class).value();
               // String fieldName = element.getSimpleName().toString();
                //stringClassHashMap.put(name,_class);
            }

        }
        for (Element element: roundEnv.getElementsAnnotatedWith(MVPInject.class)){
            if (!SuperficialValidation.validateElement(element))
                continue;
            // Start by verifying common generated code restrictions.
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            boolean hasError = isInaccessibleViaGeneratedCode(MVPInject.class, "fields", element)
                    || isBindingInWrongPackage(MVPInject.class, element);
            // Verify that the target type extends from View.
            TypeMirror elementType = element.asType();
            if (elementType.getKind() == TypeKind.TYPEVAR) {
                TypeVariable typeVariable = (TypeVariable) elementType;
                elementType = typeVariable.getUpperBound();
            }
           /* if (!isSubtypeOfType(elementType, "android.view.View") && !isInterface(elementType)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s fields must extend from View or be an interface. (%s.%s)",
                        MVPInject.class.getSimpleName(), enclosingElement.getQualifiedName(),
                        element.getSimpleName()));
                hasError = true;
            }

            if (hasError) {
                continue;
            }*/

            // Assemble information on the field.


            List<FieldMVPBinding> FieldMVPBindingList = targetClassMap.get(enclosingElement);

            if (FieldMVPBindingList == null) {
                FieldMVPBindingList = new ArrayList<>();
                targetClassMap.put(enclosingElement, FieldMVPBindingList);
            }


            String packageName = getPackageName(enclosingElement);
            TypeName targetType = TypeName.get(enclosingElement.asType());
            int id = 0;//element.getAnnotation(MVPInject.class).value();
            String fieldName = element.getSimpleName().toString();
            TypeMirror fieldType = element.asType();

            FieldMVPBinding FieldMVPBinding = new FieldMVPBinding(fieldName, fieldType, null);
            FieldMVPBindingList.add(FieldMVPBinding);
        }

        for (Map.Entry<TypeElement, List<FieldMVPBinding>> item : targetClassMap.entrySet()){
            List<FieldMVPBinding> list = item.getValue();
            if (list == null || list.size() == 0){
                continue;
            }

            TypeElement enclosingElement = item.getKey();
            String packageName = getPackageName(enclosingElement);
            ClassName typeClassName = ClassName.bestGuess(getClassName(enclosingElement, packageName));
            TypeSpec.Builder result = TypeSpec.classBuilder(getClassName(enclosingElement, packageName) + BINDING_CLASS_SUFFIX)
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T", typeClassName))
                    .addSuperinterface(ParameterizedTypeName.get(VIEW_BINDER, typeClassName));
            result.addMethod(createBindMethod(list, typeClassName));
            try {
                JavaFile.builder(packageName, result.build())
                        .addFileComment(" This codes are generated automatically. Do not modify!")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private MethodSpec createBindMethod(List<FieldMVPBinding> list, ClassName typeClassName) {
        MethodSpec.Builder result = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addAnnotation(Override.class)
                .addParameter(typeClassName, "target", Modifier.FINAL);

        for (int i = 0; i < list.size(); i++) {
            FieldMVPBinding FieldMVPBinding = list.get(i);

            String packageString = FieldMVPBinding.getType().toString();
//            String className = FieldMVPBinding.getType().getClass().getSimpleName();
            ClassName viewClass = bestGuess(packageString);
           // result.addStatement("target.$L=($T)target.findViewById($L)", FieldMVPBinding.getName(), viewClass, FieldMVPBinding.getResId());

            result.addStatement("target.$L=($T)com.cvicse.base.dagger.BaseModule.getPersenter((com.cvicse.base.mvp.IView)target,$L)", FieldMVPBinding.getName(),viewClass,stringClassHashMap.get(packageString));
        }
        return result.build();
    }


    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass,
                                                   String targetThing, Element element) {
        boolean hasError = false;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Verify method modifiers.
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s %s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        // Verify containing type.
        if (enclosingElement.getKind() != ElementKind.CLASS) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        // Verify containing class visibility is not private.
        if (enclosingElement.getModifiers().contains(Modifier.PRIVATE)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s %s may not be contained in private classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        return hasError;
    }

    private boolean isBindingInWrongPackage(Class<? extends Annotation> annotationClass,
                                            Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        if (qualifiedName.startsWith("android.")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName));
            return true;
        }
        if (qualifiedName.startsWith("java.")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName));
            return true;
        }

        return false;
    }

    private boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (otherType.equals(typeMirror.toString())) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInterface(TypeMirror typeMirror) {
        return typeMirror instanceof DeclaredType
                && ((DeclaredType) typeMirror).asElement().getKind() == ElementKind.INTERFACE;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}