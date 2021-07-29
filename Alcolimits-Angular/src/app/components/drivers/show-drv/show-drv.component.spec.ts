import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowDrvComponent } from './show-drv.component';

describe('ShowDrvComponent', () => {
  let component: ShowDrvComponent;
  let fixture: ComponentFixture<ShowDrvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowDrvComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowDrvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
