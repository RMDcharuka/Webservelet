<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login page</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif, serif;
        }

        body {
            background-color: #123a6d;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-size: cover;
            background-position: center;
        }

        .login-container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 400px;
            padding: 40px;
            text-align: center;
        }

        .header {
            margin-bottom: 30px;
        }

        .title {
            font-size: 20px;
            font-weight: bold;
            color: black;
            margin-bottom: 10px;
        }

        .subtitle {
            font-size: 14px;
            color: black;
            line-height: 1.5;
            margin-bottom: 5px;
        }

        .login-title {
            font-size: 22px;
            font-weight: bold;
            color: black;
            margin: 25px 0;
        }

        .login-form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
            text-align: left;
        }

        .form-group label {
            font-size: 14px;
            color: black;
            font-weight: 500;
        }

        .form-group input {
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: all 0.3s;
        }

        .form-group input:focus {
            outline: none;
            border-color: #022a44;
            box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
        }

        .error-message {
            color: #d32f2f;
            font-size: 12px;
            margin-top: 5px;
            display: none;
        }

        .form-options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: -10px;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 5px;
            font-size: 14px;
            color: black;
        }

        .forgot-password {
            font-size: 14px;
            color: #123a6d;
            text-decoration: none;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }

        .login-button {
            background-color: #123a6d;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 10px;
        }

        .login-button:hover {
            background-color: #123a6d;
        }

        .signup-section {
            text-align: center;
            margin-top: 25px;
            font-size: 14px;
            color: black;
        }

        .signup-link {
            color: #123a6d;
            text-decoration: none;
            font-weight: 500;
        }

        .signup-link:hover {
            text-decoration: underline;
        }

        .api-error {
            color: #d32f2f;
            font-size: 14px;
            text-align: center;
            margin-top: 15px;
            display: none;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="header">
            <h1 class="title">Welcome Back!</h1>
        </div>

        <h2 class="login-title">Login</h2>

        <form class="login-form" id="loginForm" onsubmit="return false;">
            <div class="form-group">
                <label for="email">Email</label>
                <input name="email" type="email" id="email" placeholder="Enter your email" required>
                <div class="error-message" id="emailError"></div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input name="password" type="password" id="password" placeholder="Enter your password" required>
                <div class="error-message" id="passwordError"></div>
            </div>

            <div class="form-options">
                <div class="remember-me">
                    <input type="checkbox" id="remember">
                    <label for="remember">Remember me</label>
                </div>
                <a href="#" class="forgot-password">Forgot password?</a>
            </div>

            <button type="submit" class="login-button" onclick="handleSubmit()">Login</button>
            <p class="api-error" id="apiError"></p>
        </form>

        <div class="signup-section">
            Don't have an account? <a href="signup.html" class="signup-link">Sign up now</a>
        </div>
    </div>

    <script>
        function validateEmail(email) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                return "Please enter a valid email address.";
            }
            return "";
        }

        function validatePassword(password) {
            if (password.length < 8 || password.length > 255) {
                return "Password must be between 8 and 255 characters.";
            }
            if (!/[A-Z]/.test(password)) {
                return "Password must contain at least one uppercase letter.";
            }
            if (!/\d/.test(password)) {
                return "Password must contain at least one number.";
            }
            if (!/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
                return "Password must contain at least one special character.";
            }
            return "";
        }

        function showError(elementId, message) {
            const errorElement = document.getElementById(elementId);
            errorElement.textContent = message;
            errorElement.style.display = message ? 'block' : 'none';
        }

        async function handleSubmit() {
            const form = document.getElementById('loginForm');
            const email = form.email.value.trim();
            const password = form.password.value;

            // Reset previous errors
            showError('emailError', '');
            showError('passwordError', '');
            showError('apiError', '');

            // Validate inputs
            const emailError = validateEmail(email);
            const passwordError = validatePassword(password);

            if (emailError) showError('emailError', emailError);
            if (passwordError) showError('passwordError', passwordError);

            if (emailError || passwordError) {
                return;
            }

            // Prepare data for API
            const data = {
                email: email,
                password: password
            };

            try {
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (response.status === 200) {
                    form.reset();
                    window.location.href = "/dashboard";
                } else {
                    const result = await response.json();
                    showError('apiError', result.message || 'Login failed. Please try again.');
                }
            } catch (error) {
                showError('apiError', 'An error occurred. Please try again later.');
            }
        }
    </script>
</body>
</html>
