import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutUsComponent } from './Hiring/Modules/about-us/about-us.component';
import { AddJobDialogComponent } from './Hiring/Modules/add-job-dialog/add-job-dialog.component';
import { AddTestDialogComponent } from './Hiring/Modules/add-test-dialog/add-test-dialog.component';
import { AdminUserComponent } from './Hiring/Modules/admin-user/admin-user.component';
import { ApplyDiagComponent } from './Hiring/Modules/apply-diag/apply-diag.component';
import { BusinessLoginComponent } from './Hiring/Modules/business-login/business-login.component';
import { CandidateAppComponent } from './Hiring/Modules/candidate-app/candidate-app.component';
import { CandidateJobsComponent } from './Hiring/Modules/candidate-jobs/candidate-jobs.component';
import { HomepageComponent } from './Hiring/Modules/homepage/homepage.component';
import { InterviewDiagComponent } from './Hiring/Modules/interview-diag/interview-diag.component';
import { JobAppComponent } from './Hiring/Modules/job-app/job-app.component';
import { JobDashboardComponent } from './Hiring/Modules/job-dashboard/job-dashboard.component';
import { JobDetailsComponent } from './Hiring/Modules/job-details/job-details.component';
import { JobOffersComponent } from './Hiring/Modules/job-offers/job-offers.component';
import { JobOverviewComponent } from './Hiring/Modules/job-overview/job-overview.component';
import { JobTestsComponent } from './Hiring/Modules/job-tests/job-tests.component';
import { JobViewComponent } from './Hiring/Modules/job-view/job-view.component';
import { MeetComponent } from './Hiring/Modules/meet/meet.component';
import { OfferDiagComponent } from './Hiring/Modules/offer-diag/offer-diag.component';
import { QuestionsComponent } from './Hiring/Modules/questions/questions.component';
import { RegisterComponent } from './Hiring/Modules/register/register.component';
import { SendTestComponent } from './Hiring/Modules/send-test/send-test.component';
import { SlideComponent } from './Hiring/Modules/slide/slide.component';
import { TechTestComponent } from './Hiring/Modules/tech-test/tech-test.component';
import { TestsComponent } from './Hiring/Modules/tests/tests.component';
import { TrackBoardComponent } from './Hiring/Modules/track-board/track-board.component';
import { UpdJobDialogComponent } from './Hiring/Modules/upd-job-dialog/upd-job-dialog.component';
import { UpdateTestComponent } from './Hiring/Modules/update-test/update-test.component';
import { UserApplicationsComponent } from './Hiring/Modules/user-applications/user-applications.component';

@NgModule({
  declarations: [
    AppComponent,
    AboutUsComponent,
    AddJobDialogComponent,
    AddTestDialogComponent,
    AdminUserComponent,
    ApplyDiagComponent,
    BusinessLoginComponent,
    CandidateAppComponent,
    CandidateJobsComponent,
    HomepageComponent,
    InterviewDiagComponent,
    JobAppComponent,
    JobDashboardComponent,
    JobDetailsComponent,
    JobOffersComponent,
    JobOverviewComponent,
    JobTestsComponent,
    JobViewComponent,
    MeetComponent,
    OfferDiagComponent,
    QuestionsComponent,
    RegisterComponent,
    SendTestComponent,
    SlideComponent,
    TechTestComponent,
    TestsComponent,
    TrackBoardComponent,
    UpdJobDialogComponent,
    UpdateTestComponent,
    UserApplicationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserModule,
   
    BrowserAnimationsModule,
    
    AppRoutingModule,
    HttpClientModule,
    
    BrowserAnimationsModule,

    FormsModule,MatChipsModule,RecaptchaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
