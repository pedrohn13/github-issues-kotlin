import br.com.phneto.kotlinissues.api.GitHubIssuesService
import br.com.phneto.kotlinissues.api.GithubIssueRemoteDataSource
import br.com.phneto.kotlinissues.contracts.IssueContract
import br.com.phneto.kotlinissues.presenter.IssuePresenter
import org.koin.dsl.module

val appModule = module {

    single { GithubIssueRemoteDataSource() }
    factory { GitHubIssuesService() }
    factory { (view: IssueContract.View) -> IssuePresenter(view) }

}