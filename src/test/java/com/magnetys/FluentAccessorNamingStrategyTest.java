package com.magnetys;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.val;

public class FluentAccessorNamingStrategyTest {

    @Test
    public void isGetterMethod_WithReturnAndNoParametersMethod_ReturnsTrue( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val someGetterMethod = new MethodWithReturnAndNoParameters( "getSomeProperty" );

        //Act & Assert
        assertThat( strategy.isGetterMethod( someGetterMethod ) ).isTrue( );

    }

    @Test
    public void isGetterMethod_WithReturnAndOneParameterMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithReturnAndOneParameter( );
    
        //Act & Assert
        assertThat( strategy.isGetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isGetterMethod_WithReturnAndManyParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithReturnAndManyParameters( );
    
        //Act & Assert
        assertThat( strategy.isGetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isGetterMethod_WithNoReturnAndNoParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndNoParameters( );
    
        //Act & Assert
        assertThat( strategy.isGetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isGetterMethod_WithNoReturnAndOneParameterMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndOneParameter( );
    
        //Act & Assert
        assertThat( strategy.isGetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isGetterMethod_WithNoReturnAndManyParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndManyParameters( );
    
        //Act & Assert
        assertThat( strategy.isGetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isSetterMethod_WithReturnAndOneParameterMethod_ReturnsTrue( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val someSetterMethod = new MethodWithReturnAndOneParameter( "setSomeProperty" );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( someSetterMethod ) ).isTrue( );

    }

    @Test
    public void isSetterMethod_WithReturnAndNoParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val someSetterMethod = new MethodWithReturnAndNoParameters( );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( someSetterMethod ) ).isFalse( );

    }

    @Test
    public void isSetterMethod_WithReturnAndManyParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val someSetterMethod = new MethodWithReturnAndManyParameters( );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( someSetterMethod ) ).isFalse( );

    }

    @Test
    public void isSetterMethod_WithNoReturnAndNoParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndNoParameters( );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isSetterMethod_WithNoReturnAndOneParameterMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndOneParameter( );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void isSetterMethod_WithNoReturnAndManyParametersMethod_ReturnsFalse( ) {

        //Arrange
        val strategy = new FluentAccessorNamingStrategy( );
        val doWhatever = new MethodWithNoReturnAndManyParameters( );
    
        //Act & Assert
        assertThat( strategy.isSetterMethod( doWhatever ) ).isFalse( );

    }

    @Test
    public void getPropertyName_WithSimpleMethod_ReturnsCorrectName( ) {

        //Arrange
        val someMethodName = "run some code";
        val strategy = new FluentAccessorNamingStrategy( );
        val someMethod = new SimpleMethod( someMethodName );

        //Act
        val propertyName = strategy.getPropertyName( someMethod );

        //Assert
        assertThat( propertyName ).isEqualTo( someMethodName );

    }

    //

    static class VoidType extends DeclaredTypeAdapter {

        @Override
        public TypeKind getKind( ) { 
        
            return TypeKind.VOID; 
        
        }
    
    }

    static class SomeClassType extends DeclaredTypeAdapter {

        @Override
        public TypeKind getKind( ) { 
        
            return TypeKind.DECLARED; 
        
        }
    
    }

    @NoArgsConstructor
    static class MethodWithReturnAndNoParameters extends ExecutableElementAdapter {

        public MethodWithReturnAndNoParameters( String name ) { 
                
            super( name ); 

        }

        @Override
        public TypeMirror getReturnType( ) { 

            return new SomeClassType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return Collections.emptyList( ); 

        }

    }

    static class MethodWithNoReturnAndNoParameters extends ExecutableElementAdapter {

        @Override
        public TypeMirror getReturnType( ) { 

            return new VoidType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return Collections.emptyList( ); 

        }

    }

    static class MethodWithReturnAndManyParameters extends ExecutableElementAdapter {

        @Override
        public TypeMirror getReturnType( ) { 

            return new SomeClassType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return List.of( new VariableElementAdapter( ),  new VariableElementAdapter( ) ); 

        }

    }

    @NoArgsConstructor
    static class MethodWithReturnAndOneParameter extends ExecutableElementAdapter {

        public MethodWithReturnAndOneParameter( String name ) { 
                
            super( name ); 

        }

        @Override
        public TypeMirror getReturnType( ) { 

            return new SomeClassType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return List.of( new VariableElementAdapter( ) ); 

        }

    }

    static class MethodWithNoReturnAndOneParameter extends ExecutableElementAdapter {

        @Override
        public TypeMirror getReturnType( ) { 

            return new VoidType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return Collections.singletonList( null ); 

        }

    }

    static class MethodWithNoReturnAndManyParameters extends ExecutableElementAdapter {

        @Override
        public TypeMirror getReturnType( ) { 

            return new VoidType( );

        }

        @Override
        public List<? extends VariableElement> getParameters( ) { 

            return List.of( new VariableElementAdapter( ),  new VariableElementAdapter( ) ); 

        }

    }

    static class SimpleMethod extends ExecutableElementAdapter {

        public SimpleMethod( String name ) { 
                
            super( name ); 

        }

        @Override
        public Name getSimpleName( ) { 

            return new StringName( this.name( ) ); 
            
        }

    }

    @Data
    @EqualsAndHashCode( callSuper = true )
    static class StringName extends NameAdapter {

        final String name;

        @Override
        public String toString( ) { 

            return this.name; 

        }

    }

    //

    static class VariableElementAdapter implements VariableElement{

        @Override
        public ElementKind getKind( ) { return null; }

        @Override
        public Set<Modifier> getModifiers( ) { return null; }

        @Override
        public List<? extends Element> getEnclosedElements( ) { return null; }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors( ) { return null; }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) { return null; }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) { return null; }

        @Override
        public <R, P> R accept(ElementVisitor<R, P> v, P p) { return null; }

        @Override
        public TypeMirror asType( ) { return null; }

        @Override
        public Object getConstantValue( ) { return null; }

        @Override
        public Name getSimpleName( ) { return null; }

        @Override
        public Element getEnclosingElement( ) { return null; }

    }

    static abstract class NameAdapter implements Name {

        @Override
        public int length( ) { return 0; }

        @Override
        public char charAt( int index ) { return ' '; }

        @Override
        public CharSequence subSequence( int start, int end ) { return null; }

        @Override
        public boolean contentEquals( CharSequence cs ) { return false; }
    
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static abstract class ExecutableElementAdapter implements ExecutableElement {

        String name;        

        @Override
        public ElementKind getKind( ) { return null; }

        @Override
        public Set<Modifier> getModifiers( ) { return null; }

        @Override
        public List<? extends Element> getEnclosedElements( ) { return null; }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors( ) { return null; }

        @Override
        public <A extends Annotation> A getAnnotation( Class<A> annotationType ) { return null; }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType( Class<A> annotationType ) { return null; }

        @Override
        public <R, P> R accept( ElementVisitor<R, P> v, P p ) { return null; }

        @Override
        public TypeMirror asType( ) { return null; }

        @Override
        public List<? extends TypeParameterElement> getTypeParameters( ) { return null; }

        @Override
        public TypeMirror getReturnType( ) { return null; }

        @Override
        public List<? extends VariableElement> getParameters( ) { return null; }

        @Override
        public TypeMirror getReceiverType( ) { return null; }

        @Override
        public boolean isVarArgs( ) { return false; }

        @Override
        public boolean isDefault( ) { return false; }

        @Override
        public List<? extends TypeMirror> getThrownTypes( ) { return null; }

        @Override
        public AnnotationValue getDefaultValue( ) { return null; }

        @Override
        public Element getEnclosingElement( ) { return null; }

        @Override
        public Name getSimpleName( ) { return null; }

    }

    static abstract class DeclaredTypeAdapter implements DeclaredType {

        @Override
        public TypeKind getKind( ) { return null; }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors( ) { return null; }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) { return null; }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) { return null; }

        @Override
        public <R, P> R accept(TypeVisitor<R, P> v, P p) { return null; }

        @Override
        public Element asElement( ) { return null; }

        @Override
        public TypeMirror getEnclosingType( ) { return null; }

        @Override
        public List<? extends TypeMirror> getTypeArguments( ) { return null; }
    
    }

}