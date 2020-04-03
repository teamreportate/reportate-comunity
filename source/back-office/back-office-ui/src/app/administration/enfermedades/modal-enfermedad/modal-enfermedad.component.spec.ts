import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEnfermedadComponent } from './modal-enfermedad.component';

describe('ModalEnfermedadComponent', () => {
  let component: ModalEnfermedadComponent;
  let fixture: ComponentFixture<ModalEnfermedadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalEnfermedadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalEnfermedadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
