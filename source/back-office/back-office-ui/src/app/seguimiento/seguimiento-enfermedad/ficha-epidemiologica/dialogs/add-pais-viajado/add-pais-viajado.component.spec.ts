import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPaisViajadoComponent } from './add-pais-viajado.component';

describe('AddPaisViajadoComponent', () => {
  let component: AddPaisViajadoComponent;
  let fixture: ComponentFixture<AddPaisViajadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPaisViajadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPaisViajadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
