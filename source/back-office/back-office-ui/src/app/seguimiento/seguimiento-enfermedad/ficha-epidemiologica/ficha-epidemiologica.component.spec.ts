import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FichaEpidemiologicaComponent } from './ficha-epidemiologica.component';

describe('FichaEpidemiologicaComponent', () => {
  let component: FichaEpidemiologicaComponent;
  let fixture: ComponentFixture<FichaEpidemiologicaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FichaEpidemiologicaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FichaEpidemiologicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
