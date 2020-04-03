import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeguimientoEnfermedadComponent } from './seguimiento-enfermedad.component';

describe('SeguimientoEnfermedadComponent', () => {
  let component: SeguimientoEnfermedadComponent;
  let fixture: ComponentFixture<SeguimientoEnfermedadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeguimientoEnfermedadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeguimientoEnfermedadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
