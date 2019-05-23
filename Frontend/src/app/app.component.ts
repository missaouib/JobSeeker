import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  navLinks = [
    { path: '', label: 'IT Job Offers in Poland' },
    { path: 'world', label: 'IT Job Offers in World' },
    { path: 'technology', label: 'Technology Statistics' },
    { path: 'category', label: 'Category Statistics' }
  ];
}
