import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnfermedadesComponent } from './enfermedades.component';

describe('EnfermedadesComponent', () => {
  let component: EnfermedadesComponent;
  let fixture: ComponentFixture<EnfermedadesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnfermedadesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnfermedadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
