/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    19-04-19
 * author:  fmontero
 **/

import {animate, state, style, transition, trigger} from '@angular/animations';

/**
 * ====================================================================
 * ==============================MC4-SRL===============================
 * =======================ANIMACIONES BASICAS==========================
 * ====================================================================
 *
 * Recuerde importar 'BrowserAnimationModule' en en modulo principal o en algun modulo particular.
 *
 */


export declare type AnimationDirection = 'translateX(-100%)' | 'translateX(+100%)' | 'translateY(+100%' | 'translateY(-100%';
export enum VerticalA {
  FROM_TOP = 'translateY(-100%)',
  FROM_BOTTOM = 'translateY(+100%)'
}

export enum HorizontalA {
  FROM_RIGTH = 'translateX(+100%)',
  FROM_LEFT = 'translateX(-100%)'
}

/**
 * Use esta constante para indicar una animacion    izquierda --> derecha
 * @type {string}
 */
export const FROM_LEFT = 'translateX(-100%)';

/**
 * Use esta constante para indicar una animacion    derecha --> izquierda
 * @type {string}
 */
export const FROM_RIGTH = 'translateX(+100%)';

/**
 * Use esta constante para indicar una animacion    arriba --> abajo
 * @type {string}
 */
export const FROM_TOP = 'translateY(-100%)';

/**
 * Use esta constante para indicar una animacion    abajo --> arriba
 * @type {string}
 */
export const FROM_BOTTOM = 'translateY(+100%)';

/**
 * Key de directiva para agregar en el elemento que se desea aplicar la animacion ejm:
 *
 *  <div class="content-center" >
 *      <h1>Lorem ipsum dolor sit amet consectetur adiciping elit....</h1>
 *  </div>
 *
 * @type {string}
 */
const APPEAR_ANIMATION_KEY = 'appear';

/**
 * Animacion desde el fondo hacia fuera
 * @param {number} time, tiempo de duracion entre el estado inicial y final (1000 milisegundos)
 * @param {number} initialOpacity, opacidad inicial del elemento aplicado (0.2 de 1 por defecto)
 * @returns {AnimationTriggerMetadata}
 */
export const appearAnimation = (time: number = 1000, initialOpacity: number=0)=> {
  return trigger(APPEAR_ANIMATION_KEY, [
    state('void', style({
      opacity: initialOpacity
    })),
    transition(':enter', [
      animate(time, style({
        opacity: 1
      }))
    ])
  ]);
};

/**
 * Key de directiva para agregar en el elemento que se desea aplicar la animacion ejm:
 *
 *  <div class="content-center" @translateX>
 *      <h1>Lorem ipsum dolor sit amet consectetur adiciping elit....</h1>
 *  </div>
 *
 * @type {string}
 */
const X_TRANSLATE_ANIMATION = 'translateX';

/**
 * Animacion sobre eje X
 * @param {AnimationDirection} direction, especifica la direccion de entrada, use {@link FROM_RIGTH} y {@link FROM_LEFT}
 * @param {number} time, tiempo de duracion entre el estado inicial y final (1000 milisegundos)
 * @param {number} initialOpacity, opacidad inicial del elemento aplicado (0.2 de 1 por defecto)
 * @returns {AnimationTriggerMetadata}
 */
export const horizontalAnimation = (direction: HorizontalA, time: number=1000, initialOpacity: number=0.2)=> {
  return trigger(X_TRANSLATE_ANIMATION, [
    state('void', style({
      transform: direction,
      opacity: initialOpacity
    })),
    transition(':enter', [
      animate(time, style({
        transform: 'translateX(0)',
        opacity: 1
      }))
    ])
  ])
};

/**
 * Key de directiva para agregar en el elemento que se desea aplicar la animacion ejm:
 *
 *  <div class="content-center" @translateY>
 *      <h1>Lorem ipsum dolor sit amet consectetur adiciping elit....</h1>
 *  </div>
 *
 * @type {string}
 */
const Y_TRANSLATE_ANIMATION = 'translateY';

/**
 * Animacion sobre eje Y
 * @param {AnimationDirection} direction, especifica la direccion de entrada, use {@link FROM_TOP} y {@link FROM_BOTTOM}
 * @param {number} time, tiempo de duracion entre el estado inicial y final (1000 milisegundos)
 * @param {number} initialOpacity, opacidad inicial del elemento aplicado (0.2 de 1 por defecto)
 * @returns {AnimationTriggerMetadata}
 */
export const verticalAnimation = (direction: VerticalA, time: number=1000, initialOpacity: number=0.2)=> {
  return trigger(Y_TRANSLATE_ANIMATION, [
    state('void', style({
      transform: direction,
      opacity: initialOpacity
    })),
    transition(':enter', [
      animate(time, style({
        transform: 'translateY(0)',
        opacity: 1
      }))
    ])
  ])
};
