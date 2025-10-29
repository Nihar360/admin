# Database Credentials Setup

## 🔒 Security First

**IMPORTANT**: Never hardcode or commit database passwords to your repository!

This guide shows you how to securely configure your MySQL database credentials.

## 📝 Setup Methods

### Method 1: Using .env File (Recommended)

1. Navigate to the `backend` directory
2. Copy the example file:
   ```bash
   cp .env.example .env
   ```

3. Edit `.env` and add your actual password:
   ```
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=ecommerce_db
   DB_USERNAME=root
   DB_PASSWORD=your_mysql_password
   ```
   
   **Replace `your_mysql_password` with your actual MySQL root password**

4. Run the backend:
   ```bash
   ./run.sh          # Mac/Linux
   run.bat           # Windows
   ```

**Note**: The `.env` file is automatically ignored by git (listed in `.gitignore`)

### Method 2: Environment Variables (Mac/Linux)

Export the password before running:

```bash
export DB_PASSWORD="your_mysql_password"
./run.sh
```

Or run inline:
```bash
DB_PASSWORD="your_mysql_password" ./run.sh
```

**Replace `your_mysql_password` with your actual MySQL root password**

### Method 3: Environment Variables (Windows)

Set the password before running:

```cmd
set DB_PASSWORD=your_mysql_password
run.bat
```

**Replace `your_mysql_password` with your actual MySQL root password**

### Method 4: System Environment Variables

**Mac/Linux:**
Add to `~/.bashrc` or `~/.zshrc`:
```bash
export DB_PASSWORD="your_mysql_password"
```

**Replace `your_mysql_password` with your actual MySQL root password**

**Windows:**
1. Search for "Environment Variables" in Windows
2. Click "Environment Variables"
3. Under "User variables", click "New"
4. Variable name: `DB_PASSWORD`
5. Variable value: `your_mysql_password` (use your actual password)
6. Click OK

## 📋 All Configuration Variables

You can configure these in your `.env` file:

```bash
# Required
DB_PASSWORD=your_password_here

# Optional (have defaults)
DB_HOST=localhost              # Default: localhost
DB_PORT=3306                   # Default: 3306
DB_NAME=ecommerce_db           # Default: ecommerce_db
DB_USERNAME=root               # Default: root
SERVER_PORT=8080               # Default: 8080
```

## ✅ Verification

To verify your configuration is loaded correctly:

1. Run the backend startup script
2. You should see:
   ```
   Starting E-Commerce Admin Backend...
   Database: mysql://localhost:3306/ecommerce_db
   User: root
   Server will run on: http://localhost:8080/api
   ```

3. Check for errors. If you see "ERROR: DB_PASSWORD not set", follow the setup steps above.

## 🚫 Common Mistakes to Avoid

❌ **DON'T** hardcode passwords in code  
❌ **DON'T** commit `.env` file to git  
❌ **DON'T** share your `.env` file with others  
❌ **DON'T** use the same password for development and production  

✅ **DO** use `.env` file for local development  
✅ **DO** use environment variables for production  
✅ **DO** keep `.env` in `.gitignore`  
✅ **DO** use different passwords for different environments  

## 🔐 For Production

For production deployment, use:
- Secrets management service (AWS Secrets Manager, Azure Key Vault, etc.)
- Environment variables from your hosting platform
- Never use `.env` files in production
- Rotate passwords regularly

## 📞 Troubleshooting

**Problem**: "ERROR: DB_PASSWORD environment variable is not set!"

**Solution**:
1. Make sure you created `.env` file in the `backend` directory
2. Check that `DB_PASSWORD=your_password` is in the file
3. Verify no spaces around the `=` sign
4. Ensure the file is named exactly `.env` (not `.env.txt`)

**Problem**: "Access denied for user 'root'@'localhost'"

**Solution**:
1. Verify your MySQL password is correct
2. Test MySQL connection: `mysql -u root -p`
3. Ensure MySQL server is running
4. Check that the database exists: `SHOW DATABASES;`

## 💡 Best Practices

1. **Development**: Use `.env` file
2. **Staging**: Use environment variables from hosting platform
3. **Production**: Use secrets manager service
4. **Team**: Share `.env.example` (without real passwords)
5. **Documentation**: Update `.env.example` when adding new variables

---

**Remember**: Security is not optional. Always protect your database credentials!
