import {
  BrowserRouter as Router,
  Routes,
  Route,
  Outlet,
  Navigate,
} from 'react-router-dom';

// 페이지
import MainPage from './pages/mainPage';
import ProfilePage from './pages/profilePage';
import ResultPage from './pages/imageResultPage';
import PredictPage from './pages/imagePredictPage';
import SocialLogin from './pages/socialLogin';
import ErrorPage from './pages/errorPage';
// 나브바 & 바텀시트
import Navbar from './components/common/navbar';
import { useAccessToken } from './data_source/apiInfo';
import { AxiosInterceptor } from './repository/auth/instanceRepository';

function PrivateLoginRoute() {
  const token = useAccessToken();
  if (token) {
    return <Outlet />;
  }
  return <Navigate to="/" />;
}

function PrivateNotLoginRoute() {
  const token = useAccessToken();
  if (!token) {
    return <Outlet />;
  }
  return <Navigate to="/" />;
}

function App() {
  return (
    <div className="App h-screen snap-y snap-mandatory overflow-scroll scrollbar-hidden">
      <AxiosInterceptor>
        <Router>
          <Routes>
            <Route path="/" element={<Navbar />}>
              <Route path="" element={<MainPage />} />
              <Route element={<PrivateLoginRoute />}>
                <Route path="predict" element={<PredictPage />} />
                <Route path="result" element={<ResultPage />} />
                <Route path="history" element={<ProfilePage />} />
              </Route>
              <Route element={<PrivateNotLoginRoute />}>
                <Route path="login/redirect" element={<SocialLogin />} />
              </Route>
            </Route>
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </Router>
      </AxiosInterceptor>
    </div>
  );
}

export default App;
