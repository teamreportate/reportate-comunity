import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEnfermedadComponent } from './add-enfermedad.component';

describe('AddEnfermedadComponent', () => {
  let component: AddEnfermedadComponent;
  let fixture: ComponentFixture<AddEnfermedadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEnfermedadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEnfermedadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
