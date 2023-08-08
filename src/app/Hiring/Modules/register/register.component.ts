import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, PatternValidator, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Observable } from 'rxjs';
import { BusinessAuthService } from '../../Shared/services/Business-auth.service';
import { BusinessTokenStorageService } from '../../Shared/services/Business-token-storage.service';
import { Business } from '../../Shared/entities/Business';
import { User } from 'src/app/Core/Modules/Entity/user';
import { AuthService } from 'src/app/_Core/Shared/Service/auth.service';
import { TokenStorageService } from 'src/app/_Core/Shared/Service/token-storage.service';





@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  firstForm!: FormGroup;
  secondForm!: FormGroup;
  formJoin!:FormGroup;
  
  imageUrl: any;
  imagePath:any;
 
  verifMail=false;
  verifName=false;
  matcher = new MyErrorStateMatcher();
  countries:any;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  title = 'angular-internationalization';
  
  constructor(private fb: FormBuilder,private businessAuthService: BusinessAuthService, private businesstokenStorage: BusinessTokenStorageService, private authService: AuthService, private tokenStorageService: TokenStorageService) { }

  
  ngOnInit(): void {
 
    this.GetCountry();
    this.firstForm = this.fb.group({
     
      companyName: ['', Validators.required],
      website: [''],
      nbEmployee: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      /*email: ['', Validators.required],*/
      /*password: ['', Validators.required],*/
      /*confirmPassword: ['', Validators.required]*/
    },
    { validators: this.checkPasswords }
    )
    console.log(this.firstForm)

    this.secondForm = this.fb.group({
     
      recrutementRole: ['', Validators.required],
      phone: [''],
      address: ['', Validators.required],
      country: ['', Validators.required],
      logo: [''],
      description: ['', Validators.required],
      industry: ['', Validators.required],
      checked: [false],
      
    },)


    this.formJoin=new FormGroup({form1:this.firstForm,form2:this.secondForm})
    //console.log(this.secondForm.get('checked')?.value)
    //this.ExistMail(this.firstForm.get('email')?.value)
    
  }

  match():boolean{
    this.firstForm.get('password')?.clearValidators()
    var pattern =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    //console.log(pattern.test(this.firstForm.get('password')?.value))
    return pattern.test(this.firstForm.get('password')?.value);
    
  }

  ExistMail(email:string):boolean{
    if(email.length!=0){
    this.businessAuthService.VerifMail(email).subscribe(res=>
      {
      this.verifMail=res;
      //console.log(res)
      }
      )
    }
    return this.verifMail

  }
  ExistCompany(name:string){
    if(name.length!=0){
    this.businessAuthService.VerifName(name).subscribe(res=>
      {
      this.verifName=res
      console.log(res)
      }
      )
    }

      return this.verifName
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      //console.log(field);
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

/*
   Send(){
    
   // console.log(this.firstForm.getError('pattern'))
      this.authService.registerBusiness(
      this.firstForm.get('companyName')?.value,
      /*this.firstForm.get('email')?.value,*/
      /*this.firstForm.get('password')?.value,*/
      /*this.firstForm.get('website')?.value,
      this.firstForm.get('nbEmployees')?.value,
      this.firstForm.get('firstName')?.value,
      this.firstForm.get('lastName')?.value,
      this.secondForm.get('role')?.value,
      this.secondForm.get('phone')?.value,
      this.secondForm.get('industry')?.value,
      this.secondForm.get('country')?.value,
      this.secondForm.get('physicalAddress')?.value,
      this.secondForm.get('logo')?.value,
      this.secondForm.get('description')?.value,
      new Date(),*/
     /* 
      ).subscribe(
        data => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
        err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        }
      );    
  }*/

  Send() {
    const user = this.tokenStorageService.getUser();
    const role = user.roles[0];
  
    // Check if the logged-in user has the Recruiter role
    if (role === "ROLE_RECRUITER") {
      console.error("Recruiters are not allowed to add attributes to their accounts.");
      return;
    }

    const business: Business = {
      idBusiness: null,
      email: null,
      password: null,
      companyName: this.firstForm.get('companyName')?.value,
      website: this.firstForm.get('website')?.value,
      nbEmployee: this.firstForm.get('nbEmployee')?.value,
      firstName: this.firstForm.get('firstName')?.value,
      lastName: this.firstForm.get('lastName')?.value,
      recrutementRole: this.secondForm.get('recrutementRole')?.value,
      phone: this.secondForm.get('phone')?.value,
      industry: this.secondForm.get('industry')?.value,
      country: this.secondForm.get('country')?.value,
      address: this.secondForm.get('address')?.value,
      logo: this.secondForm.get('logo')?.value,
      description: this.secondForm.get('description')?.value,
      creationDate: new Date(),
      
      
      active: true,
      enabled: true

      
    };
    console.log(business);
    // If the user is not a Recruiter, continue with the attribute addition
    this.authService.updateUserWithBusiness(user.id, business).subscribe(
      (data: any) => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      (err: any) => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
  

  GetCountry(){
    this.businessAuthService.GetCountries().subscribe(res=>{
      this.countries=res;
      console.log(this.countries)
    })
    
  }

  onFileChanged(event:any) {
    const files = event.target.files;
    if (files.length === 0)
        return;

    const mimeType = files[0].type;
    

    const reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
        this.imageUrl = reader.result; 
    }
}
 
checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => { 
  let pass = group.get('password')?.value;
  let confirmPass = group.get('confirmPassword')?.value
  return pass === confirmPass ? null : { notSame: true }
}




}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control?.invalid && control?.parent?.dirty);
    const invalidParent = !!(control?.parent?.invalid && control?.parent?.dirty);

    return invalidCtrl || invalidParent;
  }

/*
  public selectLanguage(event:any){
    this.translateService.use(event.target.value);
    
   }*/
  
}

