<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up Form</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #123a6d;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            display: flex;
            align-items: center;
            gap: 50px;
        }

        .form-box {
            background-color: #ffff;
            padding: 40px 30px;
            border-radius: 10px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        .form-box h2 span {
            border-bottom: 2px solid #123a6d;
        }

        .form-box form {
            display: flex;
            flex-direction: column;
        }

        .form-box input[type="text"],
        .form-box input[type="email"],
        .form-box input[type="password"] {
            padding: 12px;
            margin: 10px 0 5px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            outline: none;
            transition: border-color 0.3s;
        }

        .form-box input:focus {
            border-color: #123a6d;
        }

        .error-message {
            color: #d32f2f;
            font-size: 12px;
            margin-bottom: 10px;
            display: none;
        }

        .checkbox {
            display: flex;
            align-items: center;
            font-size: 14px;
            margin: 10px 0;
        }

        .checkbox input {
            margin-right: 8px;
        }

        button {
            background-color: #123a6d;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            font-weight: bold;
            margin-top: 10px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #123a6d;
        }

        .login-link {
            text-align: center;
            font-size: 14px;
            margin-top: 10px;
        }

        .login-link a {
            color: #123a6d;
            text-decoration: none;
        }

        .side-text h1 {
            color: white;
            font-size: 40px;
            font-weight: bold;
            line-height: 1.3;
            text-align: left;
        }

        .api-error {
            color: #d32f2f;
            font-size: 14px;
            text-align: center;
            margin-top: 10px;
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-box">
            <h2><span>Sign Up</span></h2>
            <form id="signupForm" onsubmit="return false;">
                <label>
                    <input name="name" type="text" placeholder="Enter your name" required>
                    <div class="error-message" id="nameError"></div>
                </label>
                <label>
                    <input name="email" type="email" placeholder="Enter your email" required>
                    <div class="error-message" id="emailError"></div>
                </label>
                <label>
                    <input name="password" type="password" placeholder="Create password" required>
                    <div class="error-message" id="passwordError"></div>
                </label>
                <div class="checkbox">
                    <input type="checkbox" id="terms" required>
                    <label for="terms">I accept all terms & conditions</label>
                </div>
                <button type="submit" onclick="handleSubmit()">Sign Up Now</button>
                <p class="api-error" id="apiError"></p>
                <p class="login-link">Already have an account? <a href="login.jsp">Login now</a></p>
            </form>
        </div>
        <div class="side-text">
            <h1>Create your own<br>Account now!</h1>
        </div>
    </div>

    <script>
        function validateName(name) {
            if (name.length < 3 || name.length > 100) {
                return "Name must be between 3 and 100 characters.";
            }
            return "";
        }

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
            const form = document.getElementById('signupForm');
            const name = form.name.value.trim();
            const email = form.email.value.trim();
            const password = form.password.value;
            const terms = document.getElementById('terms').checked;

            // Reset previous errors
            showError('nameError', '');
            showError('emailError', '');
            showError('passwordError', '');
            showError('apiError', '');

            // Validate inputs
            const nameError = validateName(name);
            const emailError = validateEmail(email);
            const passwordError = validatePassword(password);

            if (nameError) showError('nameError', nameError);
            if (emailError) showError('emailError', emailError);
            if (passwordError) showError('passwordError', passwordError);
            if (!terms) {
                showError('apiError', 'You must accept the terms and conditions.');
                return;
            }

            if (nameError || emailError || passwordError) {
                return;
            }

            // Prepare data for API
            const data = {
                name: name,
                email: email,
                password: password
            };

            try {
                const response = await fetch('/api/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (response.status === 200) {
                    // Handle successful registration (e.g., redirect or show success message)
                    form.reset();
                    window.location.href = "/login";
                } else {
                    const result = await response.json();
                    showError('apiError', result.message || 'Registration failed. Please try again.');
                }
            } catch (error) {
                showError('apiError', 'An error occurred. Please try again later.');
            }
        }
    </script>
</body>
</html>
