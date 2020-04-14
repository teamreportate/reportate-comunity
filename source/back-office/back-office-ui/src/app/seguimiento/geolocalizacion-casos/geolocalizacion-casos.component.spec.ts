import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GeolocalizacionCasosComponent } from './geolocalizacion-casos.component';

describe('GeolocalizacionCasosComponent', () => {
  let component: GeolocalizacionCasosComponent;
  let fixture: ComponentFixture<GeolocalizacionCasosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GeolocalizacionCasosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeolocalizacionCasosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
