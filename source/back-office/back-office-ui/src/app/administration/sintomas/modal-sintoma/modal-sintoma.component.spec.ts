import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalSintomaComponent } from './modal-sintoma.component';

describe('ModalSintomaComponent', () => {
  let component: ModalSintomaComponent;
  let fixture: ComponentFixture<ModalSintomaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalSintomaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalSintomaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
