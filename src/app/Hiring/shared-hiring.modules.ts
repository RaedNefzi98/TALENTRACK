

@NgModule({
    declarations: [
      HeaderComponent,
      FooterComponent,
      SideBarComponent
      
  
      
    ],
    imports: [
      CommonModule,
      RouterModule,
      MatIconModule,
      MatTooltipModule,
      MatSidenavModule,
      MatDividerModule,
      MatListModule,
      MatToolbarModule,
      MatCardModule
     
    ],
    exports:[
      HeaderComponent,
      FooterComponent,
      SideBarComponent
      
    ]
  })


  export class SharedHiring {}