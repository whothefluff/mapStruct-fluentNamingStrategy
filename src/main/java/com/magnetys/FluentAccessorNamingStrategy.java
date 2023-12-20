package com.magnetys;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

/**
 * Overrides the default naming strategy to recognize chained fluent naming
 */
@SuppressWarnings( { "ClassWithoutLogger" } )
public class FluentAccessorNamingStrategy extends DefaultAccessorNamingStrategy{

  @Override
  public boolean isGetterMethod( final ExecutableElement method ){

    return method.getParameters( ).isEmpty( ) && !( method.getReturnType( ).getKind( ).equals( TypeKind.VOID ) ); //should return not-this

  }

  @Override
  public boolean isSetterMethod( final ExecutableElement method ){

    return method.getParameters( ).size( ) == 1 && !( method.getReturnType( ).getKind( ).equals( TypeKind.VOID ) ); // should return this

  }

  @Override
  public String getPropertyName( final ExecutableElement getterOrSetterMethod ){

    return getterOrSetterMethod.getSimpleName( ).toString( );

  }

}
