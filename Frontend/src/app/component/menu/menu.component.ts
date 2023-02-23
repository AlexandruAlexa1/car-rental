import { Component } from '@angular/core';
import * as $ from 'jquery';


@Component({
  selector: 'menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  showMenu() {
    $('.menu-content').toggleClass('change');
  }
}
