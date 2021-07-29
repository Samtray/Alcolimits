import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowVhcComponent } from './show-vhc.component';

describe('ShowVhcComponent', () => {
  let component: ShowVhcComponent;
  let fixture: ComponentFixture<ShowVhcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowVhcComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowVhcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
