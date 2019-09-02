package `fun`.dofor.devhelper

interface IMvpView<out Presenter: IPresenter<IMvpView<Presenter>>> {
    val presenter: Presenter
}

interface IPresenter<out View: IMvpView<IPresenter<View>>> {
    val view: View
}