import {Directive, ElementRef, HostListener, OnInit} from '@angular/core';

@Directive({
  selector: '[sinEspacios]'
})
export class ValidarSinEspaciosDirective implements OnInit{

  private elemRef: any;

  constructor(private el: ElementRef) {
    this.elemRef = this.el.nativeElement;
  }

  ngOnInit() {
  }

  @HostListener('keypress', ['$event']) onKeyPress(event) {
    const e = <any> event;
    if (e.key === ' ') {
      e.preventDefault();
    }
  }

  @HostListener('focus', ['$event.target.value']) onFocus(value) {
    this.elemRef.value = this.elemRef.value.trim();
  }

  @HostListener('blur', ['$event.target.value']) onBlur(value) {
    this.elemRef.value = this.elemRef.value.trim();
  }
}
