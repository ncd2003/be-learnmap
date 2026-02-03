package com.learnmap.be.domain.utils;

public class Constants {
    // Lỗi hệ thống và xác thực
    public static final String INTERNAL_SERVER_ERROR = "Lỗi hệ thống nội bộ";
    public static final String INVALID_TOKEN = "Token không hợp lệ hoặc đã hết hạn";
    public static final String ACCOUNT_NOT_FOUND = "Không tìm thấy tài khoản";
    public static final String ROLE_NOT_FOUND = "Không tìm thấy vai trò";
    public static final String INVALID_PERMISSION = "Bạn không có quyền thực hiện hành động này";
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "Tên đăng nhập hoặc mật khẩu không chính xác";
    public static final String PERMISSION_DENIED = "Truy cập bị từ chối";
    public static final String PASSWORD_MISMATCH = "Mật khẩu và xác nhận mật khẩu không khớp nhau";

    // Lỗi nội dung học tập
    public static final String COURSE_NOT_FOUND = "Không tìm thấy khóa học";
    public static final String LEARNING_PATH_NOT_FOUND = "Không tìm thấy lộ trình học tập";
    public static final String CHAPTER_NOT_FOUND = "Không tìm thấy chương học";
    public static final String LESSON_NOT_FOUND = "Không tìm thấy bài học";
    public static final String RESOURCE_NOT_FOUND = "Không tìm thấy tài nguyên";
    public static final String CATEGORY_NOT_FOUND = "Không tìm thấy danh mục"; // Đã sửa từ Resource -> Category
    public static final String TOPIC_NOT_FOUND = "Không tìm thấy chủ đề";
    public static final String POST_NOT_FOUND = "Không tìm thấy bài viết";
    public static final String COMMENT_NOT_FOUND = "Không tìm thấy bình luận";
    public static final String PLAN_NOT_FOUND = "Không tìm thấy gói";
    public static final String SUBSCRIPTION_NOT_FOUND = "Không tìm thấy gói đăng ký";

    // Lỗi logic nghiệp vụ
    public static final String POSITION_DUPLICATED = "Vị trí không được phép trùng lặp";
}