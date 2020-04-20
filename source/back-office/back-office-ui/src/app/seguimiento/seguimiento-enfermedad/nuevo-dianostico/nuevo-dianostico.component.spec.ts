import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoDianosticoComponent } from './nuevo-dianostico.component';

describe('NuevoDianosticoComponent', () => {
  let component: NuevoDianosticoComponent;
  let fixture: ComponentFixture<NuevoDianosticoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuevoDianosticoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuevoDianosticoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
